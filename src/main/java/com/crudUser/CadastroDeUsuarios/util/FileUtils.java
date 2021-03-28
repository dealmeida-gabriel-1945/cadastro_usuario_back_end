package com.crudUser.CadastroDeUsuarios.util;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Foto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Objects;

public final class FileUtils {
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_CACHE_CONTROL_VALUE = "no-cache, no-store, must-revalidate";
    private static final String HEADER_PRAGMA = "Pragma";
    private static final String HEADER_PRAGMA_VALUE = "no-cache";
    private static final String HEADER_EXPIRES = "Expires";
    private static final String HEADER_EXPIRES_VALUE = "0";
    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HEADER_ATTACHMENT_FILENAME = "attachment; filename=";

    private FileUtils() {
        // Construtor Vazio
    }

    public static String BYTE_ARRAY_TO_STRING_BASE64(byte[] foto){
        if(Objects.isNull(foto)) return null;
        try {
            return new String(Base64.getEncoder().encode(foto));
        }catch (Exception e){
            return null;
        }
    }
    public static byte[] STRING_BASE64_TO_BYTE_ARRAY(String foto){
        if(Objects.isNull(foto)) return null;
        try {
            return Base64.getDecoder().decode(foto);
        }catch (Exception e){
            return null;
        }
    }

    public static ResponseEntity<InputStreamResource> output(byte[] byteArrayOutputStream, String nome) {
        return montaResponse(byteArrayOutputStream, getHttpHeaders(nome));
    }

    private static ResponseEntity<InputStreamResource> montaResponse(byte[] byteArrayOutputStream, HttpHeaders headers) {
        return ResponseEntity.ok().headers(headers).contentLength(byteArrayOutputStream.length)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream)));
    }

    private static HttpHeaders getHttpHeaders(String nome) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_CACHE_CONTROL, HEADER_CACHE_CONTROL_VALUE);
        headers.add(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
        headers.add(HEADER_EXPIRES, HEADER_EXPIRES_VALUE);
        headers.add(HEADER_CONTENT_DISPOSITION, HEADER_ATTACHMENT_FILENAME + nome);

        return headers;
    }
}
