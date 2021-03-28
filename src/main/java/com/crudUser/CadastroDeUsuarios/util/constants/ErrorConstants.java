package com.crudUser.CadastroDeUsuarios.util.constants;

public class ErrorConstants {
    public static final String ERRO_TAMANHO_ARQUIVO_EXCEDIDO_OU_FORMATO = "O tamanho do arquivo da foto é de até 10MB.";
    public static final String ERRO_REGISTRO_NAO_ENCONTRADO = "O registro não consta em nosso banco de dados.";
    public static final String ERRO_PROVIDERS = "Impossível de prover as imagens para o PDF.";
    public static final String ERRO_DEFAULT = "Ops! Algo de errado aconteceu!.";

    //ERRO NULL OBRIGATORIO
    public static final String ERRO_CAMPO_OBRIGATORIO_PT1 = "O campo de ";
    public static final String ERRO_CAMPO_OBRIGATORIO_PT2 = " é obrigatório.";
    public static final String ERRO_CAMPO_NULL_OBRIGATORIO_PT1 = "O campo de ";
    public static final String ERRO_CAMPO_NULL_OBRIGATORIO_PT2 = " deve ser nulo.";
    public static String erroCampoNullObrigatorio(String field){
        return String.join("",ERRO_CAMPO_NULL_OBRIGATORIO_PT1, field, ERRO_CAMPO_NULL_OBRIGATORIO_PT2);
    }
    public static String erroCampoObrigatorio(String field){
        return String.join("",ERRO_CAMPO_OBRIGATORIO_PT1, field, ERRO_CAMPO_OBRIGATORIO_PT2);
    }
}
