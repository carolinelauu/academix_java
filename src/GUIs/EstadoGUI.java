package GUIs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAOs.DAOEstado;
import Entidades.Estado;
import com.google.gson.Gson;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import tools.DiretorioDaAplicacao;

public class EstadoGUI extends JDialog {

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
    String acao = "";
    private JScrollPane scrollTabela = new JScrollPane();

    private final JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
    private final JPanel pnVazio = new JPanel(new GridLayout(6, 1));

    private CardLayout cardLayout;
    JLabel lbSigla_estado = new JLabel("Sigla_estado");
    JTextField tfSigla_estado = new JTextField(50);
    JLabel lbNome_estado = new JLabel("  Nome_estado");
    JTextField tfNome_estado = new JTextField(50);
    DAOEstado controle = new DAOEstado();
    Estado estado = new Estado();
    String[] colunas = new String[]{"sigla_estado", "nome_estado"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public EstadoGUI() {
        for (JButton button : Arrays.asList(btAdicionar, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarPDF)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        for (JLabel jLabel : Arrays.asList(lbNome_estado, lbSigla_estado)) {
            jLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            jLabel.setFont(new Font("Poppins", Font.CENTER_BASELINE, 12));
            jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, 25));
        }
        
        getContentPane().setBackground(new Color(183, 156, 237));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Estado");
        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbSigla_estado);
        pnNorte.add(tfSigla_estado);
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
        pnCentro.add(lbNome_estado);
        pnCentro.add(tfNome_estado);
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
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao()+"\\src\\HTML\\siteestado\\tabela_estado.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(EstadoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAOEstado daoestado = new DAOEstado();
            String jsonEstado = new Gson().toJson(daoestado.list());
            html = html.replace("jsonPessoas", jsonEstado);
            try {
                Files.write(Paths.get(da.getDiretorioDaAplicacao()+"\\src\\HTML\\siteestado\\tabela_populada_estado.html"), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(EstadoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                    // Abre o navegador Chrome com a URL especificada
                    String url = ("file:///"+da.getDiretorioDaAplicacao()+"/src/HTML/siteestado/tabela_populada_estado.html").replace("\\", "/");
                    Desktop.getDesktop().browse(new URI(url));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
        });
        // listener Buscar

        btBuscar.addActionListener(e
                -> {

            cardLayout.show(pnSul, "avisos");
            if (!tfSigla_estado.getText().equals("") && tfSigla_estado.getText().trim().length() == 2) {
                if (estado != null) {
                    estado = controle.obter(tfSigla_estado.getText());
                    if (estado != null) {
                        btAdicionar.setVisible(false);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btSalvarPDF.setVisible(false);
                        tfNome_estado.setText(String.valueOf(estado.getNome_estado()));
                        tfNome_estado.setEditable(false);
                    } else {// não achou na lista
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        btSalvarPDF.setVisible(false);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
                tfSigla_estado.selectAll();
                tfSigla_estado.requestFocus();
            }
        }
        );
        btAdicionar.addActionListener(e
                -> {
            tfSigla_estado.setEditable(false);
            tfSigla_estado.requestFocus();
            tfNome_estado.setEditable(true);
            btAdicionar.setVisible(false);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btListar.setVisible(false);
            btSalvarPDF.setVisible(false);
            acao = "adicionar";
        }
        );
        btSalvar.addActionListener(e
                -> {
            if (acao.equals("adicionar")) {
                estado = new Estado();
            }
            if (!tfNome_estado.getText().equals("")) {
                estado.setSigla_estado(tfSigla_estado.getText());
                tfSigla_estado.setText("");
                tfSigla_estado.setEditable(false);
                estado.setNome_estado(tfNome_estado.getText());
                tfNome_estado.setText("");
                tfNome_estado.setEditable(false);
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btSalvarPDF.setVisible(false);
                tfSigla_estado.setEnabled(true);
                tfSigla_estado.setEditable(true);
                tfSigla_estado.requestFocus();
                tfSigla_estado.setText("");
                tfSigla_estado.setText("");
                System.out.println("Estado adicionado: " + estado.toString());
                if (acao.equals("adicionar")) {
                    controle.inserir(estado);
                } else {
                    controle.atualizar(estado);
                }
            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (nome_estado)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
                tfNome_estado.selectAll();
                tfNome_estado.requestFocus();
            }
        }
        );
        btAlterar.addActionListener(e
                -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            tfSigla_estado.setEditable(false);
            tfNome_estado.setEditable(true);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btListar.setVisible(false);
            btExcluir.setVisible(false);
            acao = "alterar";
        }
        );
        btExcluir.addActionListener(e
                -> {
            int response = JOptionPane.showConfirmDialog(cp, "Confirme a exclusão?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            btExcluir.setVisible(false);
            btBuscar.setVisible(true);
            tfSigla_estado.setEnabled(true);
            tfSigla_estado.setEditable(true);
            tfSigla_estado.requestFocus();
            tfSigla_estado.setText("");
            tfNome_estado.setText("");
            tfNome_estado.setEditable(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(estado);
            }
        }
        );
        btListar.addActionListener(
                (ActionEvent e) -> {
                    List<Estado> listaEstado = controle.list();
                    String[] colunas = new String[]{"sigla_estado", "nome_estado"};
                    String[][] dados = new String[listaEstado.size()][colunas.length];
                    String aux[];
                    for (int i = 0; i < listaEstado.size(); i++) {
                        aux = listaEstado.get(i).toString().split(";");
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
                    tfNome_estado.setText("");

                }
        );
        btCancelar.addActionListener(e
                -> {
            btCancelar.setVisible(false);
            tfSigla_estado.setText("");
            tfSigla_estado.requestFocus();
            tfSigla_estado.setEnabled(true);
            tfSigla_estado.setEditable(true);
            tfNome_estado.setText("");
            tfNome_estado.setEditable(false);
            btBuscar.setVisible(true);
            btListar.setVisible(true);
            btSalvar.setVisible(false);
            btCancelar.setVisible(false);
            btSalvarPDF.setVisible(false);

        }
        );

        // listener ao fechar o programa
        addWindowListener(
                new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e
            ) {
                // antes de sair, salvar a lista em armazenamento permanente
                // Sai da classe
                dispose();
            }
        }
        );

        setModal(
                true);
        pack();

        setLocationRelativeTo(
                null);// centraliza na tela
        setVisible(
                true);
    }// fim do contrutor de GUI
} // fim da classe
