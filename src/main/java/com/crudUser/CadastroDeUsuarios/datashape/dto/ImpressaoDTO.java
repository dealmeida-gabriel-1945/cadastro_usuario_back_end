package com.crudUser.CadastroDeUsuarios.datashape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ImpressaoDTO implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private String nomeArquivo;
    private byte[] bytesDocumnento = new byte[0];
}
