package GUIs;

import DAOs.DAOCidade;
import Entidades.Cidade;
import com.itextpdf.text.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tools.DiretorioDaAplicacao;

public class CriarPDFCidade {

    public CriarPDFCidade(String nomeEntidade) {
        Document document = new Document();
        DiretorioDaAplicacao da = new DiretorioDaAplicacao();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(da.getDiretorioDaAplicacao() + "\\src\\PDF\\" + "PDF" + nomeEntidade + ".pdf"));
            System.out.println(da.getDiretorioDaAplicacao() + "\\src\\PDF" + "PDF" + nomeEntidade + ".pdf");

            document.open();

            document.setPageSize(PageSize.A4);

            Font f = new Font(FontFamily.COURIER, 20, Font.BOLD);
            Paragraph p1 = new Paragraph(nomeEntidade, f);

            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setSpacingAfter(20);
            document.add(new Paragraph(p1));
            document.add(new Paragraph("  "));

            PdfPCell header = new PdfPCell();

            header.setBorderWidthBottom(1.0f);
            header.setBorder(Rectangle.AUTHOR);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorder(Rectangle.LEFT);
            header.setColspan(2);
            DAOCidade daocidade = new DAOCidade();
            PdfPTable table = new PdfPTable(3);
            table.addCell("id_cidade");
            table.addCell("nome_cidade");
            table.addCell("estado_sigla_estado");
            List<Cidade> cidades = daocidade.list();
            for (int i = 0; i < cidades.size(); i++) {
                Cidade cidade = cidades.get(i);
                table.addCell(String.valueOf(cidade.getId_cidade()));
                table.addCell(String.valueOf(cidade.getNome_cidade()));
                table.addCell(String.valueOf(cidade.getEstado_sigla_estado()));
                table.setWidthPercentage(100.0f);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);

            }
            document.add(table);
            document.add(new Paragraph("  "));
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }
}
