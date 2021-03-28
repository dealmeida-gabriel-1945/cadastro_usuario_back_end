package com.crudUser.CadastroDeUsuarios.service.impl;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Template;
import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.dto.ImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.datashape.enumerations.ImpressaoEnum;
import com.crudUser.CadastroDeUsuarios.datashape.enumerations.TemplateEnum;
import com.crudUser.CadastroDeUsuarios.mapper.UsuarioMapper;
import com.crudUser.CadastroDeUsuarios.repository.TemplateRepository;
import com.crudUser.CadastroDeUsuarios.repository.UsuarioRepository;
import com.crudUser.CadastroDeUsuarios.service.UsuarioService;
import com.crudUser.CadastroDeUsuarios.service.resolvers.StaticTemplateExecutor;
import com.crudUser.CadastroDeUsuarios.util.FileUtils;
import com.crudUser.CadastroDeUsuarios.util.constants.ErrorConstants;
import com.crudUser.CadastroDeUsuarios.util.constants.FileConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.ImageProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    private TemplateRepository templateRepository;
    @Autowired
    private ImageProvider imageProvider;
    @Autowired
    private CSSResolver cssResolver;

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
        Template template = templateRepository.findById(TemplateEnum.PDF_LISTAGEM_GERAL.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_REGISTRO_NAO_ENCONTRADO, null));
        try{
            Document document = new Document(PageSize.A4);

            Context context = getContext(ImpressaoEnum.IMPRESSAO_GERAL.geraParametros(repository.findAll()));
            StandardMessageResolver messageResolver = new StandardMessageResolver();
            StaticTemplateExecutor executor = new StaticTemplateExecutor(context, messageResolver, StandardTemplateModeHandlers.HTML5.getTemplateModeName());
            String templateProcessado = executor.processTemplateCode(template.getConteudo());


            ByteArrayOutputStream documentoStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, documentoStream);

            InputStream templateStream = new ByteArrayInputStream(templateProcessado.getBytes(StandardCharsets.UTF_8));

            document.open();

            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(imageProvider);

            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            p.parse(templateStream);


            document.close();

            return new ImpressaoDTO(FileConstants.NOME_LISTAGEM_PDF, documentoStream.toByteArray());


        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_DEFAULT, null);
        }
    }


    private Context getContext(Map<String, Object> params) {
        Context ctx = new Context();
        params.forEach(ctx::setVariable);
        return ctx;
    }

}
