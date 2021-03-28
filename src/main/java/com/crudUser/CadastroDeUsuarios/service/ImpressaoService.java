package com.crudUser.CadastroDeUsuarios.service;

import com.crudUser.CadastroDeUsuarios.datashape.dto.ImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.impressao.PdfConfigurationDTO;

import java.util.Map;

public interface ImpressaoService {
    ImpressaoDTO montaPdf(PdfConfigurationDTO configuracao, Map<String, Object> params);
}
