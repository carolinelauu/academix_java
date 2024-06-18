package GUIs;

import Entidades.Universidade;
import com.itextpdf.text.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tools.ManipulaArquivo;

public class CriarPDFUniversidade {

    public CriarPDFUniversidade(String nomeEntidade, String grafico) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("C:\\Users\\carol\\OneDrive\\Documents\\IdeaProjects\\Academix\\PDF\\" + "PDF"
                            + nomeEntidade + ".pdf"));

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
            List<Universidade> universidades = carregarDados("Universidade.csv");
            for (int i = 0; i < universidades.size(); i++) {
                PdfPTable table = new PdfPTable(4);
                Universidade universidade = universidades.get(i);
                table.addCell("id_universidade");
                table.addCell("nome_universidade");
                table.addCell("privada");
                table.addCell("sigla_universidade");
                table.addCell(String.valueOf(universidade.getId_universidade()));
                table.addCell(String.valueOf(universidade.getNome_universidade()));
                table.addCell(String.valueOf(universidade.getPrivada()));
                table.addCell(String.valueOf(universidade.getSigla_universidade()));
                table.setWidthPercentage(100.0f);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);
            }
            document.add(new Paragraph("  "));
            Image png = Image.getInstance(grafico);

            document.add(png);
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }

    public List<Universidade> carregarDados(String caminho) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        if (!manipulaArquivo.existeOArquivo(caminho)) {
            manipulaArquivo.criarArquivoVazio(caminho);
        }
        List<Universidade> lista = new ArrayList<>();

        List<String> listaDeString = manipulaArquivo.abrirArquivo(caminho);
        // converter de CSV para Universidade
        Universidade universidade;
        for (String string : listaDeString) {
            String aux[] = string.split(";");
            universidade = new Universidade(Integer.parseInt(aux[0]), aux[1], aux[2]=="true"?Short.valueOf("1"):Short.valueOf("0"), aux[3]);
            lista.add(universidade);
        }
        return lista;
    }
}