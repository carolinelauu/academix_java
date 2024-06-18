package GUIs;

import Entidades.Universidade;
import DAOs.DAOUniversidade;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFileChooser;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Container;
import java.awt.Desktop;
import tools.CopiarArquivos;
import tools.DiretorioDaAplicacao;
import tools.ImagemAjustada;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UniversidadeGUI extends JDialog {

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
    JButton btSalvarPDF = new JButton("Salvar Tabela");
    JButton btAdicionarFoto = new JButton("Adicionar/alterar foto");
    JButton btRemoverFoto = new JButton("Remover foto");
    String acao = "";
    private JScrollPane scrollTabela = new JScrollPane();

    private JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
    private JPanel pnVazio = new JPanel(new GridLayout(6, 1));

    private CardLayout cardLayout;
    JLabel lbId_universidade = new JLabel("Id_universidade");
    JTextField tfId_universidade = new JTextField(50);
    JLabel lbNome_universidade = new JLabel("  Nome_universidade");
    JTextField tfNome_universidade = new JTextField(50);
    JLabel lbPrivada = new JLabel("  Privada");
    private final JCheckBox cbPrivada = new JCheckBox("Privada", false);
    JLabel lbSigla_universidade = new JLabel("  Sigla_universidade");
    JTextField tfSigla_universidade = new JTextField(50);
    DAOUniversidade controle = new DAOUniversidade();
    Universidade universidade = new Universidade();
    String[] colunas = new String[]{"id_universidade", "nome_universidade", "privada", "sigla_universidade"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);
    ImagemAjustada imagemAjustada = new ImagemAjustada();
    DiretorioDaAplicacao diretorioDaAplicacao = new DiretorioDaAplicacao();
    String dirApp = diretorioDaAplicacao.getDiretorioDaAplicacao();
    String origem = dirApp + "/src/fotos/silhueta.png";
    int tamX = 300;
    int tamY = 300;
    String temFoto = "";

    public UniversidadeGUI() {
        for (JButton button : Arrays.asList(btAdicionar, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarPDF)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Universidade");

        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);
        pnLesteA.add(btAdicionarFoto);
        pnLesteA.add(btRemoverFoto);
        btAdicionarFoto.setVisible(false);
        btRemoverFoto.setVisible(false);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbId_universidade);
        pnNorte.add(tfId_universidade);
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
        pnCentro.add(lbNome_universidade);
        pnCentro.add(tfNome_universidade);
        pnCentro.add(lbPrivada);
        pnCentro.add(cbPrivada);
        pnCentro.add(lbSigla_universidade);
        pnCentro.add(tfSigla_universidade);
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

        btSalvarPDF.addActionListener(e -> {
            String html = null;
            DiretorioDaAplicacao da = new DiretorioDaAplicacao();
            try {
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\siteuniversidade\\tabela_universidade.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(UniversidadeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            DAOUniversidade daouniversidade = new DAOUniversidade();
            List<Universidade> listauni = daouniversidade.list();
            String json = "[";
            for (Universidade uni: listauni) {
                json += "{";
                json += "\"id_universidade\":\""+uni.getId_universidade()+"\",";
                json += "\"nome_universidade\":\""+uni.getNome_universidade()+"\",";
                json += "\"privada\":\""+(Objects.equals(uni.getPrivada(), Short.valueOf("1"))?"sim":"não")+"\",";
                json += "\"sigla_universidade\":\""+uni.getSigla_universidade()+"\"},";
            }
            json += "]";
            html = html.replace("jsonPessoas", json);
            try {
                Files.write(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\siteuniversidade\\tabela_populada_universidade.html"), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(UniversidadeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                // Abre o navegador Chrome com a URL especificada
                String url = ("file:///" + da.getDiretorioDaAplicacao() + "/src/HTML/siteuniversidade/tabela_populada_universidade.html").replace("\\", "/");
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        // listener Buscar
        btBuscar.addActionListener(e -> {

            cardLayout.show(pnSul, "avisos");
            int valor;
            try {
                valor = Integer.parseInt(tfId_universidade.getText());
                if (universidade != null) {
                    universidade = controle.obter(valor);
                    System.out.println(universidade.toString());
                    if (universidade != null) {
                        btAdicionar.setVisible(false);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btSalvarPDF.setVisible(false);
                        System.out.println("oi antes nome");
                        tfNome_universidade.setText(String.valueOf(universidade.getNome_universidade()));
                        tfNome_universidade.setEditable(false);
                        System.out.println("oi");
                        cbPrivada.setSelected(universidade.getPrivada() == Short.valueOf("1") ? true : false);
                        cbPrivada.setEnabled(false);
                        tfSigla_universidade.setText(String.valueOf(universidade.getSigla_universidade()));
                        System.out.println("oioi");
                        tfSigla_universidade.setEditable(false);
                    } else {// não achou na lista
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        btSalvarPDF.setVisible(false);
                        cbPrivada.setSelected(false);
                        cbPrivada.setEnabled(false);
                    }
                }
            } catch (Exception excep) {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
                tfId_universidade.selectAll();
                tfId_universidade.requestFocus();
            }
        });
        btAdicionar.addActionListener(e -> {
            tfId_universidade.setEditable(false);
            tfId_universidade.requestFocus();
            tfNome_universidade.setEditable(true);
            cbPrivada.setEnabled(true);
            tfSigla_universidade.setEditable(true);
            btAdicionar.setVisible(false);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btListar.setVisible(false);
            btAdicionarFoto.setVisible(true);
            btRemoverFoto.setVisible(true);
            btSalvarPDF.setVisible(false);
            acao = "adicionar";
            temFoto = "Nao";
        });
        btSalvar.addActionListener(e -> {
            new CopiarArquivos();
            if (acao.equals("adicionar")) {
                universidade = new Universidade();
            }
            if (!tfNome_universidade.getText().equals("")) {
                if (!tfSigla_universidade.getText().equals("")) {
                    universidade.setId_universidade(Integer.parseInt(tfId_universidade.getText()));
                    tfId_universidade.setText("");
                    tfId_universidade.setEditable(false);
                    universidade.setNome_universidade(tfNome_universidade.getText());
                    tfNome_universidade.setText("");
                    tfNome_universidade.setEditable(false);
                    universidade.setPrivada(cbPrivada.isSelected() == true ? Short.valueOf("1") : Short.valueOf("0"));
                    cbPrivada.setSelected(false);
                    cbPrivada.setEnabled(false);
                    universidade.setSigla_universidade(tfSigla_universidade.getText());
                    tfSigla_universidade.setText("");
                    tfSigla_universidade.setEditable(false);
                    btSalvar.setVisible(false);
                    btCancelar.setVisible(false);
                    btBuscar.setVisible(true);
                    btListar.setVisible(true);
                    btSalvarPDF.setVisible(false);
                    btAdicionarFoto.setVisible(false);
                    btRemoverFoto.setVisible(false);
                    tfId_universidade.setEnabled(true);
                    tfId_universidade.setEditable(true);
                    tfId_universidade.requestFocus();
                    tfId_universidade.setText("");
                    tfId_universidade.setText("");
                    if (acao.equals("adicionar")) {
                        controle.inserir(universidade);
                    } else {
                        controle.atualizar(universidade);
                    }
                } else {
                    JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (sigla_universidade)", "Erro ao adicionar",
                            JOptionPane.PLAIN_MESSAGE);
                    tfSigla_universidade.selectAll();
                    tfSigla_universidade.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (nome_universidade)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
                tfNome_universidade.selectAll();
                tfNome_universidade.requestFocus();
            }
        });
        btAlterar.addActionListener(e -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            tfId_universidade.setEditable(false);
            tfNome_universidade.setEditable(true);
            cbPrivada.setEnabled(true);
            tfSigla_universidade.setEditable(true);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btListar.setVisible(false);
            btExcluir.setVisible(false);
            btAdicionarFoto.setVisible(true);
            btRemoverFoto.setVisible(true);
            acao = "alterar";
        });
        btExcluir.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(cp, "Confirme a exclusão?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            btExcluir.setVisible(false);
            btBuscar.setVisible(true);
            tfId_universidade.setEnabled(true);
            tfId_universidade.setEditable(true);
            tfId_universidade.requestFocus();
            tfId_universidade.setText("");
            tfNome_universidade.setText("");
            tfNome_universidade.setEditable(false);
            cbPrivada.setSelected(false);
            cbPrivada.setEnabled(false);
            tfSigla_universidade.setText("");
            tfSigla_universidade.setEditable(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(universidade);
            }
        });
        btListar.addActionListener(e -> {
            List<Universidade> listaUniversidade = controle.list();
            String[] colunas = new String[]{"id_universidade", "nome_universidade", "privada", "sigla_universidade"};
            String[][] dados = new String[listaUniversidade.size()][colunas.length];
            String aux[];
            for (int i = 0; i < listaUniversidade.size(); i++) {
                aux = listaUniversidade.get(i).toString().split(";");
                for (int j = 0; j < colunas.length; j++) {
                    dados[i][j] = aux[j];
                }
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
            tfId_universidade.setText("");
            tfId_universidade.requestFocus();
            tfId_universidade.setEnabled(true);
            tfId_universidade.setEditable(true);
            tfNome_universidade.setText("");
            tfNome_universidade.setEditable(false);
            cbPrivada.setSelected(false);
            cbPrivada.setEnabled(false);
            tfSigla_universidade.setText("");
            tfSigla_universidade.setEditable(false);
            btBuscar.setVisible(true);
            btListar.setVisible(true);
            btSalvar.setVisible(false);
            btCancelar.setVisible(false);
            btAdicionarFoto.setVisible(false);
            btRemoverFoto.setVisible(false);
            btSalvarPDF.setVisible(false);

        });
        btAdicionarFoto.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fc.showOpenDialog(cp) == JFileChooser.APPROVE_OPTION) {
                fc.getSelectedFile();
                origem = fc.getSelectedFile().getAbsolutePath();

                try {

                    temFoto = "Sim";
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cp, "Erro ao carregar a imagem");
                }
            }
        });

        btRemoverFoto.addActionListener(e -> {
            temFoto = "Nao";

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
