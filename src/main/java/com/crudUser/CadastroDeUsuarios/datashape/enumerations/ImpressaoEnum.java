package com.crudUser.CadastroDeUsuarios.datashape.enumerations;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.enumerations.stategies.ImpressaoStrategy;
import com.crudUser.CadastroDeUsuarios.service.builders.PdfParamsBuilder;
import com.crudUser.CadastroDeUsuarios.util.constants.CamposConstants;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public enum ImpressaoEnum implements ImpressaoStrategy {
    IMPRESSAO_GERAL{
        @Override
        public Map<String, Object> geraParametros(List<Usuario> usuarios) {
            return new PdfParamsBuilder()
                    .addParamIfNotNull(CamposConstants.CAMPO_USUARIOS, usuarios)
                    .addParamIfNotNull(CamposConstants.CAMPO_DATA_EXPEDICAO, LocalDate.now())
                    .build();

        }
    }
}
