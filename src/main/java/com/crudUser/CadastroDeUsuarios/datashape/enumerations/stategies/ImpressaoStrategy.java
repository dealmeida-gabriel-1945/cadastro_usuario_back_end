package com.crudUser.CadastroDeUsuarios.datashape.enumerations.stategies;

import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioImpressaoDTO;

import java.util.List;
import java.util.Map;

public interface ImpressaoStrategy {
    Map<String, Object> geraParametros(List<UsuarioImpressaoDTO> usuarios);
}
