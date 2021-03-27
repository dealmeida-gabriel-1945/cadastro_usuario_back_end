package com.crudUser.CadastroDeUsuarios.mapper;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "foto", target = "foto", qualifiedByName = "fotoToBase64")
    UsuarioDTO toDto(Usuario source);
    @Mapping(source = "foto", target = "foto", qualifiedByName = "fotoToByteArray")
    Usuario toDomain(UsuarioDTO source);

    @Named("fotoToByteArray")
    default byte[] fotoToBlob(String foto){
        if(Objects.isNull(foto)) return null;
        try {
            return Base64.getDecoder().decode(foto);
        }catch (Exception e){
            return null;
        }
    }

    @Named("fotoToBase64")
    default String fotoToByteArray(byte[] foto){
        if(Objects.isNull(foto)) return null;
        try {
            return new String(Base64.getEncoder().encode(foto));
        }catch (Exception e){
            return null;
        }
    }
}
