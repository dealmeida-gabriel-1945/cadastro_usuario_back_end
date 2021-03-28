package com.crudUser.CadastroDeUsuarios.service.impl;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.dto.ImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.impressao.PdfConfigurationDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.datashape.enumerations.ImpressaoEnum;
import com.crudUser.CadastroDeUsuarios.datashape.enumerations.TemplateEnum;
import com.crudUser.CadastroDeUsuarios.mapper.UsuarioMapper;
import com.crudUser.CadastroDeUsuarios.repository.TemplateRepository;
import com.crudUser.CadastroDeUsuarios.repository.UsuarioRepository;
import com.crudUser.CadastroDeUsuarios.service.ImpressaoService;
import com.crudUser.CadastroDeUsuarios.service.UsuarioService;
import com.crudUser.CadastroDeUsuarios.util.FileUtils;
import com.crudUser.CadastroDeUsuarios.util.constants.ErrorConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final String NOME_ENTIDADE = "Area";

    @Autowired
    private UsuarioMapper mapper;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private ImpressaoService impressaoService;
    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public UsuarioDTO cadastrar(UsuarioDTO toSave) throws IOException, SQLException {
        if(Objects.nonNull(toSave.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.erroCampoNullObrigatorio("id"), null);
        }
        if(Objects.nonNull(toSave.getFoto())){
            verificaTamanhoArquivo(new SerialBlob(Base64.getDecoder().decode(toSave.getFoto())));
        }
        return mapper.toDto(repository.save(mapper.toDomain(toSave)));
    }

    @Override
    public Page<UsuarioDTO> listarPagina(Pageable pageable) {
        return this.repository.findAllSemFoto(pageable).map(this.mapper::toDto);
    }

    @Override
    public void deleteUsuario(Long id) {
        this.repository.delete(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_REGISTRO_NAO_ENCONTRADO, null)));
    }

    @Override
    public UsuarioDTO editaUsuario(Long id, UsuarioDTO toEdit) throws SQLException {
        if(Objects.isNull(id) || Objects.isNull(toEdit.getId()) || (!Objects.equals(id, toEdit.getId()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.erroCampoObrigatorio("id"), null);
        }

        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_REGISTRO_NAO_ENCONTRADO, null));

        if(Objects.nonNull(toEdit.getFoto())){
            verificaTamanhoArquivo(new SerialBlob(Base64.getDecoder().decode(toEdit.getFoto())));
        }

        return mapper.toDto(repository.save(mapper.toDomain(toEdit)));
    }

    @Override
    public String buscaFoto(Long id) {
        if(Objects.isNull(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.erroCampoObrigatorio("id"), null);
        }
        Usuario aux = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_REGISTRO_NAO_ENCONTRADO, null));
        return (Objects.nonNull(aux.getFoto())) ? FileUtils.BYTE_ARRAY_TO_STRING_BASE64(aux.getFoto().getArquivo()) : "";
    }

    private void verificaTamanhoArquivo(Blob foto) throws SQLException {
        if(Objects.nonNull(foto) && ((foto.length()/1000) > 10000)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_TAMANHO_ARQUIVO_EXCEDIDO_OU_FORMATO, null);
        }
    }

    @Override
    public ImpressaoDTO imprimirUsuarios() {
        PdfConfigurationDTO configuracao = new PdfConfigurationDTO(
            templateRepository.findById(TemplateEnum.PDF_LISTAGEM_GERAL.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_REGISTRO_NAO_ENCONTRADO, null)),
            Boolean.TRUE
        );
        return impressaoService.montaPdf( configuracao, ImpressaoEnum.IMPRESSAO_GERAL.geraParametros(mapper.toImpressaoDto(repository.findAll())));
    }



}
