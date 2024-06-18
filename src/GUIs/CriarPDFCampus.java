package GUIs;

import DAOs.DAOEstado;
import DAOs.DAOUniversidade;
import Entidades.Campus;
import Entidades.Estado;
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

public class CriarPDFCampus {

    public CriarPDFCampus(String nomeEntidade, String grafico) {
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
            List<Campus> campuss = carregarDados("Campus.csv");
            for (int i = 0; i < campuss.size(); i++) {
                PdfPTable table = new PdfPTable(4);
                Campus campus = campuss.get(i);
                table.addCell("address_cep");
                table.addCell("nome_campus");
                table.addCell("telefone");
                table.addCell("universidade_id_universidade");
                table.addCell(String.valueOf(campus.getAddress_cep()));
                table.addCell(String.valueOf(campus.getNome_campus()));
                table.addCell(String.valueOf(campus.getTelefone()));
                table.addCell(String.valueOf(campus.getUniversidade_id_universidade()));
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

    public List<Campus> carregarDados(String caminho) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        if (!manipulaArquivo.existeOArquivo(caminho)) {
            manipulaArquivo.criarArquivoVazio(caminho);
        }
        List<Campus> lista = new ArrayList<>();

        List<String> listaDeString = manipulaArquivo.abrirArquivo(caminho);
        //converter de CSV para Campus
        Campus campus;
        for (String string : listaDeString) {
            String aux[] = string.split(";");
            DAOUniversidade daouniversidade = new DAOUniversidade();
            List<Universidade> listaUniversidades = daouniversidade.listInOrderNome();
            Universidade universidadeescolhida = new Universidade();
            for (Universidade universidade : listaUniversidades) {
                String aux1[] = universidade.toString().split(";");
                if (aux[2].equals(aux1[1])) {
                    universidadeescolhida = universidade;
                }
                
            }
            campus = new Campus(Integer.parseInt(aux[0]), aux[1], aux[2], universidadeescolhida);
            lista.add(campus);
        }
        return lista;
    }
}
