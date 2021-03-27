package com.crudUser.CadastroDeUsuarios.util;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Foto;

import java.util.Base64;
import java.util.Objects;

public class FileUtils {
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
}
