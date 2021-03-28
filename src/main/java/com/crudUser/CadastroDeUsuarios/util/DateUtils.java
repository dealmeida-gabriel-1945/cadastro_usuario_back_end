package com.crudUser.CadastroDeUsuarios.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    public static String PADRAO_DATA_DD_MM_YYY = "dd/mm/yyyy";

    public static String formataData(LocalDate data, String padrao){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }
}
