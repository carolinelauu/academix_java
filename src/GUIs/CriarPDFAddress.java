package GUIs;

import DAOs.DAOCidade;
import Entidades.Address;
import Entidades.Cidade;
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

public class CriarPDFAddress {

    public CriarPDFAddress(String nomeEntidade, String grafico) {
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
            List<Address> addresss = carregarDados("Address.csv");
            for (int i = 0; i < addresss.size(); i++) {
                PdfPTable table = new PdfPTable(4);
                Address address = addresss.get(i);
                table.addCell("cep");
                table.addCell("logradouro");
                table.addCell("number");
                table.addCell("cidade_id_cidade");
                table.addCell(String.valueOf(address.getCep()));
                table.addCell(String.valueOf(address.getLogradouro()));
                table.addCell(String.valueOf(address.getNumber()));
                table.addCell(String.valueOf(address.getCidade_id_cidade()));
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

    public List<Address> carregarDados(String caminho) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        if (!manipulaArquivo.existeOArquivo(caminho)) {
            manipulaArquivo.criarArquivoVazio(caminho);
        }
        List<Address> lista = new ArrayList<>();

        List<String> listaDeString = manipulaArquivo.abrirArquivo(caminho);
        //converter de CSV para Address
        Address address;
        for (String string : listaDeString) {
            String aux[] = string.split(";");
            DAOCidade daocidade = new DAOCidade();
            List<Cidade> listaCidades = daocidade.listInOrderNome();
            Cidade cidadeescolhida = new Cidade();
            for (Cidade cidade : listaCidades) {
                String aux1[] = cidade.toString().split(";");
                if (aux[3].equals(aux1[1])) {
                    cidadeescolhida = cidade;
                }
                
            }
            address = new Address(Integer.parseInt(aux[0]), aux[1], Integer.parseInt(aux[2]), cidadeescolhida);
            lista.add(address);
        }
        return lista;
    }
}
