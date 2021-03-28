package com.crudUser.CadastroDeUsuarios.datashape.enumerations.stategies;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;

import java.util.List;
import java.util.Map;

public interface ImpressaoStrategy {
    Map<String, Object> geraParametros(List<Usuario> usuarios);
}
