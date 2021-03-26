package com.crudUser.CadastroDeUsuarios.service.impl;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.mapper.UsuarioMapper;
import com.crudUser.CadastroDeUsuarios.repository.UsuarioRepository;
import com.crudUser.CadastroDeUsuarios.util.constants.ErrorConstants;
import com.crudUser.CadastroDeUsuarios.util.constants.FileConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        return this.repository.findAll(pageable).map(this.mapper::toDto);
    }

    private void verificaTamanhoArquivo(Blob foto) throws SQLException {
        if(Objects.nonNull(foto) && ((foto.length()/1000) > 10000)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_TAMANHO_ARQUIVO_EXCEDIDO_OU_FORMATO, null);
        }
    }
}
