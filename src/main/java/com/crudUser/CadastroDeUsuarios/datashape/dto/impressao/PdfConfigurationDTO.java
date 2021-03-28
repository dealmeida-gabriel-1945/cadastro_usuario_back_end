package com.crudUser.CadastroDeUsuarios.datashape.dto.impressao;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Template;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PdfConfigurationDTO {
    private Template template;
    private Boolean marcaDagua = Boolean.FALSE;
}
