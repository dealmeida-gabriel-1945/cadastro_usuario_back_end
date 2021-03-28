package com.crudUser.CadastroDeUsuarios.mapper;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Foto;
import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.util.DateUtils;
import com.crudUser.CadastroDeUsuarios.util.FileUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "foto", target = "foto", qualifiedByName = "fotoToString")
    UsuarioDTO toDto(Usuario source);
    @Mapping(source = "foto", target = "foto", qualifiedByName = "stringToFoto")
    Usuario toDomain(UsuarioDTO source);
    @Mapping(source = "foto", target = "foto", qualifiedByName = "fotoToString")
    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "formataDataToString")
    UsuarioImpressaoDTO toImpressaoDto(Usuario source);

    List<UsuarioDTO> toDto(List<Usuario> source);
    List<UsuarioImpressaoDTO> toImpressaoDto(List<Usuario> source);

    @Named("stringToFoto")
    default Foto stringToFoto(String foto){
        if(Objects.isNull(foto)) return null;
        try {
            return new Foto(FileUtils.STRING_BASE64_TO_BYTE_ARRAY(foto));
        }catch (Exception e){
            return null;
        }
    }

    @Named("fotoToString")
    default String fotoToString(Foto foto){
        if(Objects.isNull(foto)) return "";
        return FileUtils.BYTE_ARRAY_TO_STRING_BASE64(foto.getArquivo());
    }

    @Named("formataDataToString")
    default String formataDataToString(LocalDate dataNascimento){
        return DateUtils.formataData(dataNascimento, DateUtils.PADRAO_DATA_DD_MM_YYY);
    }
}
