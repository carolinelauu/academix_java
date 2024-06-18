package GUIs;

import DAOs.DAOCampus;
import DAOs.DAOCampus_has_Curso;
import DAOs.DAOCurso;
import Entidades.Campus;
import Entidades.Curso;
import Entidades.CursoHasCampus;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class FiltrarGUI extends JDialog {

    Container cp;
    
    JPanel pnNorte = new JPanel();
    JPanel pnCentro = new JPanel();
    JPanel pnSul = new JPanel();
    JPanel pnLeste = new JPanel(new BorderLayout());
    JPanel pnLesteA = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel pnLesteB = new JPanel(new GridLayout(1, 1));
    JButton btListar = new JButton("Filtrar");
    String acao = "";
    private JScrollPane scrollTabela = new JScrollPane();

    private final JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
    private final JPanel pnVazio = new JPanel(new GridLayout(6, 1));

    private CardLayout cardLayout;
    JLabel lbSite = new JLabel("Site");
    JTextField tfSite = new JTextField(50);

    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    JLabel lbEstado = new JLabel("  Estado");
    JComboBox cbEstado = new JComboBox(comboBoxModel);

    DefaultComboBoxModel comboBoxModel2 = new DefaultComboBoxModel();
    JLabel lbCurso = new JLabel("  Curso");
    JComboBox cbCurso = new JComboBox(comboBoxModel2);

    DAOCampus_has_Curso controle = new DAOCampus_has_Curso();
    CursoHasCampus cc = new CursoHasCampus();
    String[] colunas = new String[]{"nome_campus", "nome_curso", "site"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public FiltrarGUI() {
        for (JButton button : Arrays.asList(btListar)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        DAOCampus daocampus = new DAOCampus();
        List<Campus> listaCampus = daocampus.list();
        for (Campus campus : listaCampus) {
            comboBoxModel.addElement(campus.getNome_campus());
        }

        DAOCurso daocurso = new DAOCurso();
        List<Curso> listaCursos = daocurso.list();
        for (Curso curso : listaCursos) {
            comboBoxModel2.addElement(curso.getNomeCurso());
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("Filtrar");
        
        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbEstado);
        pnNorte.add(cbEstado);
        pnNorte.add(lbCurso);
        pnNorte.add(cbCurso);
        pnNorte.add(btListar);
        pnCentro.setLayout(new GridLayout(colunas.length - 1, 2));
        pnCentro.add(lbSite);
        pnCentro.add(tfSite);
        cardLayout = new CardLayout();
        pnSul.setLayout(cardLayout);
        

        for (int i = 0; i < 5; i++) {
            pnVazio.add(new JLabel(" "));
        }
        pnSul.add(pnVazio, "vazio");
        pnSul.add(pnAvisos, "avisos");
        pnSul.add(pnListagem, "listagem");
        tabela.setEnabled(false);

        pnAvisos.add(new JLabel("Avisos"));

        btListar.addActionListener(e -> {
            List<CursoHasCampus> listacc = controle.list();
            List<String> listinha = new ArrayList<>();
            for (CursoHasCampus ccc : listacc) {
                String x = ccc.getCampus().getNome_campus() + ";" + ccc.getCurso().getNomeCurso() + ";" + ccc.getSite();
                listinha.add(x);
            }
            String[] colunas = new String[]{"nome_campus", "nome_curso", "site"};
            String[][] dados = new String[listacc.size()][colunas.length];
            String aux[];
            for (int i = 0; i < listinha.size(); i++) {
                aux = listinha.get(i).toString().split(";");
                System.arraycopy(aux, 0, dados[i], 0, colunas.length);
            }
            cardLayout.show(pnSul, "listagem");
            scrollTabela.setPreferredSize(tabela.getPreferredSize());
            pnListagem.add(scrollTabela);
            scrollTabela.setViewportView(tabela);
            model.setDataVector(dados, colunas);

        });

        // listener ao fechar o programa
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // antes de sair, salvar a lista em armazenamento permanente
                // Sai da classe
                dispose();
            }
        });

        setModal(true);
        pack();
        setLocationRelativeTo(null);// centraliza na tela
        setVisible(true);
    }// fim do contrutor de GUI
} // fim da classe
