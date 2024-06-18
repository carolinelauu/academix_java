package GUIs;

import java.awt.Container;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        Container cp = getContentPane();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Menu Principal");
        cp.setLayout(new GridLayout(4, 2));
        JButton btCidade = new JButton("Cidade");
        btCidade.setBackground(new Color(149, 127, 239));
        btCidade.setFont(new Font("Poppins", Font.BOLD, 15));
        btCidade.setForeground(Color.white);
        cp.add(btCidade);
        btCidade.addActionListener(e -> {
            new CidadeGUI();
        });
        JButton btAddress = new JButton("Address");
        btAddress.setBackground(new Color(149, 127, 239));
        btAddress.setFont(new Font("Poppins", Font.BOLD, 15));
        btAddress.setForeground(Color.white);
        cp.add(btAddress);
        btAddress.addActionListener(e -> {
            try {
                new AddressGUI();
            } catch (ParseException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JButton btCampus = new JButton("Campus");
        btCampus.setBackground(new Color(149, 127, 239));
        btCampus.setFont(new Font("Poppins", Font.BOLD, 15));
        btCampus.setForeground(Color.white);
        cp.add(btCampus);
        btCampus.addActionListener(e -> {
            new CampusGUI();
        });
        JButton btUniversidade = new JButton("Universidade");
        btUniversidade.setBackground(new Color(149, 127, 239));
        btUniversidade.setFont(new Font("Poppins", Font.BOLD, 15));
        btUniversidade.setForeground(Color.white);
        cp.add(btUniversidade);
        btUniversidade.addActionListener(e -> {
            new UniversidadeGUI();
        });
        JButton btEstado = new JButton("Estado");
        btEstado.setBackground(new Color(149, 127, 239));
        btEstado.setFont(new Font("Poppins", Font.BOLD, 15));
        btEstado.setForeground(Color.white);
        cp.add(btEstado);
        btEstado.addActionListener(e -> {
            new EstadoGUI();
        });
        JButton btCurso = new JButton("Curso");
        btCurso.setBackground(new Color(149, 127, 239));
        btCurso.setFont(new Font("Poppins", Font.BOLD, 15));
        btCurso.setForeground(Color.white);
        cp.add(btCurso);
        btCurso.addActionListener(e -> {
            new CursoGUI();
        });
        
        JButton btCC = new JButton("Campus has Curso");
        btCC.setBackground(new Color(149, 127, 239));
        btCC.setFont(new Font("Poppins", Font.BOLD, 15));
        btCC.setForeground(Color.white);
        cp.add(btCC);
        btCC.addActionListener(e -> {
            new CampusCursoGUI();
        });
        JButton btSite = new JButton("Site");
        btSite.setBackground(new Color(149, 127, 239));
        btSite.setFont(new Font("Poppins", Font.BOLD, 15));
        btSite.setForeground(Color.white);
        cp.add(btSite);
        btSite.addActionListener(e -> {
            try {
                //Abre o navegador Chrome com a URL especificada
                String url = ("http://localhost:5500/");
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
            }
        });
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}