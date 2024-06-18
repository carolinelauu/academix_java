package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExtrairHTMLJSP {
    public static WebDriver driver;
    public static void main(String[] args) throws IOException {
        
        // Especificar o caminho do driver do Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\LOUISE\\OneDrive\\Academix\\Academix\\src\\chromedriver.exe");
        
        // Criar uma nova instância do ChromeDriver
        driver = new ChromeDriver();
        
        // Navegar até a página JSP que deseja extrair o HTML
        driver.get("C:\\Users\\LOUISE\\OneDrive\\Academix\\Academix\\src\\JSPs\\estado.jsp");
        
        // Obter o HTML gerado pelo arquivo JSP
        String html = driver.getPageSource();
        System.out.println(html);
        // Fechar o navegador
        driver.quit();
        
        DiretorioDaAplicacao da = new DiretorioDaAplicacao();
        // Gravar o HTML em um arquivo local
        File file = new File(da.getDiretorioDaAplicacao()+"\\src\\HTMl\\arquivo.html");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(html);
        writer.close();
    }

}

