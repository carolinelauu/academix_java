package GUIs;

import Entidades.Estado;
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

public class CriarPDFEstado {

    public CriarPDFEstado(String nomeEntidade, String grafico) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\carol\\OneDrive\\Documents\\IdeaProjects\\Academix\\PDF\\" + "PDF" + nomeEntidade + ".pdf"));

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
            List<Estado> estados = carregarDados("Estado.csv");
            for (int i = 0; i < estados.size(); i++) {
               PdfPTable table = new PdfPTable(2);                Estado estado = estados.get(i);
table.addCell("sigla_estado");table.addCell("nome_estado");table.addCell(String.valueOf(estado.getSigla_estado()));table.addCell(String.valueOf(estado.getNome_estado()));                table.setWidthPercentage(100.0f);
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
public List<Estado> carregarDados(String caminho) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        if (!manipulaArquivo.existeOArquivo(caminho)) {
            manipulaArquivo.criarArquivoVazio(caminho);
        }List<Estado> lista = new ArrayList<>();

        List<String> listaDeString = manipulaArquivo.abrirArquivo(caminho);
        //converter de CSV para Estado
        Estado estado;
        for (String string : listaDeString) {
            String aux[] = string.split(";");
estado = new Estado(aux[0], aux[1]);
lista.add(estado);
        }return lista;
    }}