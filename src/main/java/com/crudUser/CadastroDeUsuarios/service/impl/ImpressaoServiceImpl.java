package com.crudUser.CadastroDeUsuarios.service.impl;

import com.crudUser.CadastroDeUsuarios.datashape.dto.ImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.impressao.MarcaDaguaDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.impressao.PdfConfigurationDTO;
import com.crudUser.CadastroDeUsuarios.service.ImpressaoService;
import com.crudUser.CadastroDeUsuarios.service.resolvers.StaticTemplateExecutor;
import com.crudUser.CadastroDeUsuarios.util.constants.ErrorConstants;
import com.crudUser.CadastroDeUsuarios.util.constants.FileConstants;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class ImpressaoServiceImpl implements ImpressaoService {
    @Autowired
    private ImageProvider imageProvider;
    @Autowired
    private CSSResolver cssResolver;

    @Override
    public ImpressaoDTO montaPdf(PdfConfigurationDTO configuracao, Map<String, Object> params) {
        try{
            Document document = new Document(PageSize.A4);

            Context context = getContext(params);
            StandardMessageResolver messageResolver = new StandardMessageResolver();
            StaticTemplateExecutor executor = new StaticTemplateExecutor(context, messageResolver, StandardTemplateModeHandlers.HTML5.getTemplateModeName());
            String templateProcessado = executor.processTemplateCode(configuracao.getTemplate().getConteudo());


            ByteArrayOutputStream documentoStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, documentoStream);

            InputStream templateStream = new ByteArrayInputStream(templateProcessado.getBytes(StandardCharsets.UTF_8));

            if (configuracao.getMarcaDagua()){
                writer.setPageEvent(new MarcaDaguaDTO(
                    "Devel Code",
                        Font.FontFamily.HELVETICA,
                        70,
                        Font.NORMAL,
                        new BaseColor(0.058f, 0.44f, 0.70f, 0.45f)
                ));
            }

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
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_DEFAULT, null);
        }
    }

    private Context getContext(Map<String, Object> params) {
        Context ctx = new Context();
        params.forEach(ctx::setVariable);
        return ctx;
    }
}
