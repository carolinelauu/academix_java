package GUIs;

import Entidades.Cidade;
import DAOs.DAOCidade;
import DAOs.DAOEstado;
import DAOs.DAOUniversidade;
import Entidades.Estado;
import Entidades.Universidade;
import com.google.gson.Gson;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import tools.DiretorioDaAplicacao;

public class CidadeGUI extends JDialog {

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
    JLabel lbId_cidade = new JLabel("Id_cidade");
    JTextField tfId_cidade = new JTextField(50);
    JLabel lbNome_cidade = new JLabel("  Nome_cidade");
    JTextField tfNome_cidade = new JTextField(50);

    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    JLabel lbSigla_Estado = new JLabel("  Estado");
    JComboBox cbSigla_Estado = new JComboBox(comboBoxModel);

    DAOCidade controle = new DAOCidade();
    Cidade cidade = new Cidade();
    String[] colunas = new String[]{"id_cidade", "nome_cidade", "estado_sigla_estado"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public CidadeGUI() {
        for (JButton button : Arrays.asList(btAdicionar, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarPDF)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        
        DAOEstado daoestado = new DAOEstado();
        List<Estado> listaEstados = daoestado.listInOrderNome();
        for (Estado estado : listaEstados) {
            String aux[] = estado.toString().split(";");
            comboBoxModel.addElement(aux[0]);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Cidade");

        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbId_cidade);
        pnNorte.add(tfId_cidade);
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
        pnCentro.add(lbNome_cidade);
        pnCentro.add(tfNome_cidade);
        pnCentro.add(lbSigla_Estado);
        pnCentro.add(cbSigla_Estado);
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
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecidade\\tabela_cidade.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(CidadeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAOCidade daocidade = new DAOCidade();
            List<Cidade> listacidade = daocidade.list();
            String json = "[";
            for (Cidade cidade: listacidade) {
                json += "{";
                json += "\"id_cidade\":\""+cidade.getId_cidade()+"\",";
                json += "\"nome_cidade\":\""+cidade.getNome_cidade()+"\",";
                json += "\"estado_sigla_estado\":\""+cidade.getEstado_sigla_estado().toString().split(";")[0]+"\"},";
            }
            json += "]";
            html = html.replace("jsonPessoas", json);
            try {
                Files.write(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecidade\\tabela_populada_cidade.html"), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(CidadeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //Abre o navegador Chrome com a URL especificada
                String url = ("file:///" + da.getDiretorioDaAplicacao() + "/src/HTML/sitecidade/tabela_populada_cidade.html").replace("\\", "/");
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
                valor = Integer.parseInt(tfId_cidade.getText());
                if (cidade != null) {
                    cidade = controle.obter(valor);
                    if (cidade != null) {
                        btAdicionar.setVisible(false);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btSalvarPDF.setVisible(false);
                        tfNome_cidade.setText(String.valueOf(cidade.getNome_cidade()));
                        tfNome_cidade.setEditable(false);
                        cbSigla_Estado.setSelectedItem((cidade.getEstado_sigla_estado().toString().split(";")[0]));
                        cbSigla_Estado.setEnabled(false);
                    } else {// não achou na lista
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        btSalvarPDF.setVisible(false);
                    }
                }
            } catch (NumberFormatException ex1) {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
                tfId_cidade.selectAll();
                tfId_cidade.requestFocus();
            }
        });
        btAdicionar.addActionListener(e -> {
            tfId_cidade.setEditable(false);
            tfId_cidade.requestFocus();
            tfNome_cidade.setEditable(true);
            cbSigla_Estado.setEnabled(true);
            btAdicionar.setVisible(false);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btListar.setVisible(false);
            btSalvarPDF.setVisible(false);
            acao = "adicionar";
        });
        btSalvar.addActionListener(e -> {
            if (acao.equals("adicionar")) {
                cidade = new Cidade();
            }
            if (!tfNome_cidade.getText().equals("")) {
                cidade.setId_cidade(Integer.valueOf(tfId_cidade.getText()));
                tfId_cidade.setText("");
                tfId_cidade.setEditable(false);
                cidade.setNome_cidade(tfNome_cidade.getText());
                tfNome_cidade.setText("");
                tfNome_cidade.setEditable(false);
                
                Estado estadoescolhido = new Estado();
                for (Estado estado : listaEstados) {
                    String aux[] = estado.toString().split(";");
                    if (String.valueOf(cbSigla_Estado.getSelectedItem()).equals(aux[0])) {
                        estadoescolhido = estado;
                    }
                }
                cidade.setEstado_sigla_estado(estadoescolhido);
                cbSigla_Estado.setEditable(false);
                
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btSalvarPDF.setVisible(false);
                tfId_cidade.setEnabled(true);
                tfId_cidade.setEditable(true);
                tfId_cidade.requestFocus();
                tfId_cidade.setText("");
                tfId_cidade.setText("");
                if (acao.equals("adicionar")) {
                    controle.inserir(cidade);
                } else {
                    controle.atualizar(cidade);
                }

            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (nome_cidade)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
                tfNome_cidade.selectAll();
                tfNome_cidade.requestFocus();
            }
        });
        btAlterar.addActionListener(e -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            tfId_cidade.setEditable(false);
            tfNome_cidade.setEditable(true);
            cbSigla_Estado.setEnabled(true);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btListar.setVisible(false);
            btExcluir.setVisible(false);
            acao = "alterar";
        });
        btExcluir.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(cp, "Confirme a exclusão?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            btExcluir.setVisible(false);
            btBuscar.setVisible(true);
            tfId_cidade.setEnabled(true);
            tfId_cidade.setEditable(true);
            tfId_cidade.requestFocus();
            tfId_cidade.setText("");
            tfNome_cidade.setText("");
            tfNome_cidade.setEditable(false);
            cbSigla_Estado.setEditable(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(cidade);
            }
        });
        btListar.addActionListener(e -> {
            List<Cidade> listaCidade = controle.list();
            String[] colunas = new String[]{"id_cidade", "nome_cidade", "sigla_estado"};
            String[][] dados = new String[listaCidade.size()][colunas.length];
            String aux[];
            for (int i = 0; i < listaCidade.size(); i++) {
                aux = listaCidade.get(i).toString().split(";");
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
            tfId_cidade.setText("");
            tfId_cidade.requestFocus();
            tfId_cidade.setEnabled(true);
            tfId_cidade.setEditable(true);
            tfNome_cidade.setText("");
            tfNome_cidade.setEditable(false);
            cbSigla_Estado.setEditable(false);
            btBuscar.setVisible(true);
            btListar.setVisible(true);
            btSalvar.setVisible(false);
            btCancelar.setVisible(false);
            btSalvarPDF.setVisible(false);

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
