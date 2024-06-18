package GUIs;

import DAOs.DAOCampus;
import DAOs.DAOCampus_has_Curso;
import DAOs.DAOCurso;
import Entidades.Campus;
import Entidades.Curso;
import Entidades.CursoHasCampus;
import Entidades.CursoHasCampusPK;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import tools.DiretorioDaAplicacao;

public class CampusCursoGUI extends JDialog {

    Container cp;
    
    JPanel pnNorte = new JPanel();
    JPanel pnCentro = new JPanel();
    JPanel pnSul = new JPanel();
    JPanel pnLeste = new JPanel(new BorderLayout());
    JPanel pnLesteA = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel pnLesteB = new JPanel(new GridLayout(1, 1));
    JButton btBuscar = new JButton("Buscar");
    JButton btAdicionar = new JButton("Adicionar");
    JButton btSalvar = new JButton("Salvar");
    JButton btAlterar = new JButton("Alterar");
    JButton btExcluir = new JButton("Excluir");
    JButton btListar = new JButton("Listar");
    JButton btCancelar = new JButton("Cancelar");
    JButton btSite = new JButton("Entrar");
    JButton btSalvarPDF = new JButton("Salvar Tabela");
    String acao = "";
    private JScrollPane scrollTabela = new JScrollPane();

    private final JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
    private final JPanel pnVazio = new JPanel(new GridLayout(6, 1));

    private CardLayout cardLayout;
    JLabel lbSite = new JLabel("Site");
    JTextField tfSite = new JTextField(50);

    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    JLabel lbCampus = new JLabel("  Campus");
    JComboBox cbCampus = new JComboBox(comboBoxModel);

    DefaultComboBoxModel comboBoxModel2 = new DefaultComboBoxModel();
    JLabel lbCurso = new JLabel("  Curso");
    JComboBox cbCurso = new JComboBox(comboBoxModel2);

    DAOCampus_has_Curso controle = new DAOCampus_has_Curso();
    CursoHasCampus cc = new CursoHasCampus();
    String[] colunas = new String[]{"nome_campus", "nome_curso", "site"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public CampusCursoGUI() {
        for (JButton button : Arrays.asList(btAdicionar, btSite, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarPDF)) {
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
        setTitle("CRUD - Campus has Curso");
        
        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbCampus);
        pnNorte.add(cbCampus);
        pnNorte.add(lbCurso);
        pnNorte.add(cbCurso);
        pnNorte.add(btBuscar);
        pnNorte.add(btAdicionar);
        pnNorte.add(btAlterar);
        pnNorte.add(btExcluir);
        pnNorte.add(btListar);
        pnNorte.add(btSalvar);
        pnNorte.add(btCancelar);
        pnNorte.add(btSalvarPDF);
        btSalvar.setVisible(false);
        btAdicionar.setVisible(false);
        btAlterar.setVisible(false);
        btExcluir.setVisible(false);
        btCancelar.setVisible(false);
        btSalvarPDF.setVisible(false);
        pnCentro.setLayout(new GridLayout(colunas.length - 1, 2));
        pnCentro.add(lbSite);
        pnCentro.add(tfSite);
        pnCentro.add(btSite);
        cardLayout = new CardLayout();
        pnSul.setLayout(cardLayout);
        btSalvar.setVisible(false);
        btSite.setVisible(false);
        

        for (int i = 0; i < 5; i++) {
            pnVazio.add(new JLabel(" "));
        }
        pnSul.add(pnVazio, "vazio");
        pnSul.add(pnAvisos, "avisos");
        pnSul.add(pnListagem, "listagem");
        tabela.setEnabled(false);

        pnAvisos.add(new JLabel("Avisos"));

        btSalvarPDF.addActionListener(e -> {
            String html = null;
            DiretorioDaAplicacao da = new DiretorioDaAplicacao();
            try {
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecampus_curso\\tabela_campus_curso.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(CampusCursoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAOCampus_has_Curso daocc = new DAOCampus_has_Curso();
            List<CursoHasCampus> listacc = daocc.list();
            String json = "[";
            for (CursoHasCampus cc : listacc) {
                json += "{";
                json += "\"nome_campus\":\"" + cc.getCampus().getNome_campus() + "\",";
                json += "\"nome_curso\":\"" + cc.getCurso().getNomeCurso() + "\",";
                json += "\"site\":\"" + cc.getSite() + "\"},";
            }
            json += "]";
            html = html.replace("jsonPessoas", json);
            try {
                Files.write(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecampus_curso\\tabela_populada_campus_curso.html"), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(CampusCursoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //Abre o navegador Chrome com a URL especificada
                String url = ("file:///" + da.getDiretorioDaAplicacao() + "/src/HTML/sitecampus_curso/tabela_populada_campus_curso.html").replace("\\", "/");
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
            }
        });

        // listener Buscar
        btBuscar.addActionListener(e -> {

            cardLayout.show(pnSul, "avisos");
            try {
                CursoHasCampusPK chasc = new CursoHasCampusPK();

                List<Campus> listaCampus2 = daocampus.list();
                int cep;
                Campus caescolhido = new Campus();
                for (Campus campus2 : listaCampus2) {
                    if (cbCampus.getSelectedItem() == campus2.getNome_campus()) {
                        cep = campus2.getAddress_cep();
                        caescolhido = campus2;
                        chasc.setCampusAddressCep(cep);
                    }
                }

                List<Curso> listaCurso2 = daocurso.list();
                int curso;
                Curso cescolhido = new Curso();
                for (Curso curso2 : listaCurso2) {
                    if (cbCurso.getSelectedItem() == curso2.getNomeCurso()) {
                        curso = curso2.getIdCurso();
                        chasc.setCursoIdCurso(curso);
                        cescolhido = curso2;
                    }
                }
                CursoHasCampus cursoHasCampus = new CursoHasCampus();
                cc = controle.obter2(chasc);
                String site = "";
                List<CursoHasCampus> lista = controle.listarComSite();
                for (CursoHasCampus chc : lista) {
                    if (chc.getCampus() == caescolhido && chc.getCurso() == cescolhido) {
                        site = chc.getSite();
                    }
                }
                if (cc != null) {
                    btAdicionar.setVisible(false);
                    btAlterar.setVisible(true);
                    btExcluir.setVisible(true);
                    btSalvarPDF.setVisible(false);
                    tfSite.setText(site);
                    tfSite.setEditable(false);
                    if (!tfSite.getText().isEmpty()) {
                        btSite.setVisible(true);
                    } else{
                        btSite.setVisible(false);
                    }
                } else {// não achou na lista
                    btSite.setVisible(false);
                    btAdicionar.setVisible(true);
                    btAlterar.setVisible(false);
                    btExcluir.setVisible(false);
                    btSalvarPDF.setVisible(false);
                    tfSite.setText("");
                    tfSite.setEditable(false);
                }
            } catch (NumberFormatException ex1) {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
            }
        });
        btAdicionar.addActionListener(e -> {
            cbCampus.setEnabled(false);
            cbCurso.setEnabled(false);
            tfSite.setEditable(true);
            btAdicionar.setVisible(false);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btListar.setVisible(false);
            btSalvarPDF.setVisible(false);
            acao = "adicionar";
            btSite.setVisible(false);
        });
        btSalvar.addActionListener(e -> {
            if (acao.equals("adicionar")) {
                cc = new CursoHasCampus();
            }
            CursoHasCampusPK chasc = new CursoHasCampusPK();

            List<Campus> listaCampus2 = daocampus.list();
            int cep;
            Campus caescolhido = new Campus();
            for (Campus campus2 : listaCampus2) {
                if (cbCampus.getSelectedItem() == campus2.getNome_campus()) {
                    cep = campus2.getAddress_cep();
                    caescolhido = campus2;
                    chasc.setCampusAddressCep(cep);
                }
            }

            List<Curso> listaCurso2 = daocurso.list();
            int curso;
            Curso cescolhido = new Curso();
            for (Curso curso2 : listaCurso2) {
                if (cbCurso.getSelectedItem() == curso2.getNomeCurso()) {
                    curso = curso2.getIdCurso();
                    chasc.setCursoIdCurso(curso);
                    cescolhido = curso2;
                }
            }
            if (acao.equals("alterar")) {
                cc = controle.obter2(chasc);
            }
            String site = tfSite.getText();
            if (!tfSite.getText().equals("")) {
                cc.setCampus(caescolhido);
                tfSite.setText("");
                tfSite.setEditable(false);
                cc.setCurso(cescolhido);
                cc.setSite(site);
                cbCampus.setEditable(false);
                cbCurso.setEditable(false);
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btSalvarPDF.setVisible(false);
                cbCampus.setEnabled(true);
                cbCurso.setEnabled(true);
                if (acao.equals("adicionar")) {
                    
                    cc.setCursoHasCampusPK(chasc);
                    controle.inserir(cc);
                } else {
                    controle.atualizar(cc);
                }

            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (site)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
        btAlterar.addActionListener(e -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            cbCampus.setEnabled(false);
            cbCurso.setEnabled(false);
            tfSite.setEditable(true);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btListar.setVisible(false);
            btExcluir.setVisible(false);
            btSite.setVisible(false);
            acao = "alterar";
        });
        btExcluir.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(cp, "Confirme a exclusão?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            btExcluir.setVisible(false);
            btBuscar.setVisible(true);
            cbCampus.setEnabled(true);
            cbCurso.setEnabled(true);
            tfSite.setText("");
            tfSite.setEditable(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(cc);
            }
        });
        btListar.addActionListener(e -> {
            btSite.setVisible(false);
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

            btAlterar.setVisible(false);
            btExcluir.setVisible(false);
            btAdicionar.setVisible(false);
            btSalvarPDF.setVisible(true);
        });
        btCancelar.addActionListener(e -> {
            btCancelar.setVisible(false);
            cbCurso.setEnabled(true);
            cbCurso.setEditable(true);
            cbCampus.setEnabled(true);
            cbCampus.setEditable(true);
            tfSite.setText("");
            tfSite.setEditable(false);
            btBuscar.setVisible(true);
            btListar.setVisible(true);
            btSalvar.setVisible(false);
            btCancelar.setVisible(false);
            btSalvarPDF.setVisible(false);

        });
        btSite.addActionListener(e -> {
            try {
                //Abre o navegador Chrome com a URL especificada
                String url = (tfSite.getText());
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
            }
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
