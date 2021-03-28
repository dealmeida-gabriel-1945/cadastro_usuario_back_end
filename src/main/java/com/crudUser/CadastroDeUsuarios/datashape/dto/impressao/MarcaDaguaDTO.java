package com.crudUser.CadastroDeUsuarios.datashape.dto.impressao;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MarcaDaguaDTO extends PdfPageEventHelper {
    private String texto;
    private Font.FontFamily fontFamily;
    private int tamanho;
    private int decoration;
    private BaseColor cor;

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContentUnder();
        ColumnText
            .showTextAligned(
                canvas,
                Element.ALIGN_CENTER,
                new Phrase(this.texto, new Font(this.fontFamily, this.tamanho, this.decoration, this.cor)),
                PageSize.A4.getWidth() / 2,
                PageSize.A4.getWidth() / 2,
                0);
    }

}
