package com.crudUser.CadastroDeUsuarios.service.providers;

import com.crudUser.CadastroDeUsuarios.util.constants.ErrorConstants;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sun.misc.IOUtils;

import java.io.IOException;


public class Base64ImageProvider extends AbstractImageProvider {
    private static final String PREFIX_DATA = "data:";

    @Override
    public Image retrieve(String src) {
        try {
            return retrieveFromBytes(src);
        } catch (BadElementException | IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorConstants.ERRO_PROVIDERS, null);
        }
    }

    private Image retrieveFromBytes(String src) throws IOException, BadElementException {
        byte[] img = Base64.decode(src);
        return Image.getInstance(img);
    }

    private Image retrieveFromPath(String src) throws IOException, BadElementException {
        return Image.getInstance(getBytesFromResource(src));
    }

    private byte[] getBytesFromResource(String resourcePath) throws IOException {
        return IOUtils.readAllBytes(new ClassPathResource(resourcePath).getInputStream());
    }

    @Override
    public String getImageRootPath() {
        return null;
    }

}
