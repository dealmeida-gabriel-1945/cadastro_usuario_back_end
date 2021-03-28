package com.crudUser.CadastroDeUsuarios.datashape.enumerations;

import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.datashape.enumerations.stategies.ImpressaoStrategy;
import com.crudUser.CadastroDeUsuarios.service.builders.PdfParamsBuilder;
import com.crudUser.CadastroDeUsuarios.util.DateUtils;
import com.crudUser.CadastroDeUsuarios.util.constants.CamposConstants;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public enum ImpressaoEnum implements ImpressaoStrategy {
    IMPRESSAO_GERAL{
        @Override
        public Map<String, Object> geraParametros(List<UsuarioImpressaoDTO> usuarios) {
            return new PdfParamsBuilder()
                    .addParamIfNotNull(CamposConstants.CAMPO_USUARIOS, usuarios)
                    .addParamIfNotNull(CamposConstants.CAMPO_DATA_EXPEDICAO, DateUtils.formataData(LocalDate.now(), DateUtils.PADRAO_DATA_DD_MM_YYY))
                    .build();

        }
    }
}
