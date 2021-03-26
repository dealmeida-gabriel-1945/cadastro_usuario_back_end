package com.crudUser.CadastroDeUsuarios.util.constants;

public class ErrorConstants {
    public static final String ERRO_TAMANHO_ARQUIVO_EXCEDIDO_OU_FORMATO = "O tamanho do arquivo da foto é de até 10MB.";

    //ERRO NULL OBRIGATORIO
    public static final String ERRO_CAMPO_NULL_OBRIGATORIO_PT1 = "O campo de ";
    public static final String ERRO_CAMPO_NULL_OBRIGATORIO_PT2 = " deve ser nulo.";
    public static String erroCampoNullObrigatorio(String field){
        return String.join("",ERRO_CAMPO_NULL_OBRIGATORIO_PT1, field, ERRO_CAMPO_NULL_OBRIGATORIO_PT2);
    }
}
