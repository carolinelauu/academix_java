package GUIs;

import DAOs.DAOAddress;
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

import DAOs.DAOCampus;
import DAOs.DAOUniversidade;
import Entidades.Address;
import Entidades.Campus;
import Entidades.Universidade;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import tools.DiretorioDaAplicacao;

public class CampusGUI extends JDialog {

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
    JButton btSalvarHTML = new JButton("Salvar Tabela");
    String acao = "";
    private JScrollPane scrollTabela = new JScrollPane();

    private final JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
    private final JPanel pnVazio = new JPanel(new GridLayout(6, 1));

    private CardLayout cardLayout;
    JLabel lbAddress_cep = new JLabel("Address_cep");
    JTextField tfAddress_cep = new JTextField(50);
    JLabel lbNome_campus = new JLabel("  Nome_campus");
    JTextField tfNome_campus = new JTextField(50);
    JLabel lbTelefone = new JLabel("  Telefone");
    JTextField tfTelefone = new JTextField(50);

    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    JLabel lbUniversidade_id_universidade = new JLabel("  Endereço");
    JComboBox cbUniversidade_id_universidade = new JComboBox(comboBoxModel);

    DAOCampus controle = new DAOCampus();
    Campus campus = new Campus();
    String[] colunas = new String[]{"address_cep", "nome_campus", "telefone", "universidade_id_universidade"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public CampusGUI() {
        
        for (JButton button : Arrays.asList(btAdicionar, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarHTML)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        DAOUniversidade daouniversidade = new DAOUniversidade();
        List<Universidade> listaUniversidades = daouniversidade.listInOrderNome();
        for (Universidade universidade : listaUniversidades) {
            String aux[] = universidade.toString().split(";");
            comboBoxModel.addElement(aux[1]);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Campus");

        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbAddress_cep);
        pnNorte.add(tfAddress_cep);
        pnNorte.add(btBuscar);
        pnNorte.add(btAdicionar);
        pnNorte.add(btAlterar);
        pnNorte.add(btExcluir);
        pnNorte.add(btListar);
        pnNorte.add(btSalvar);
        pnNorte.add(btCancelar);
        pnNorte.add(btSalvarHTML);
        btSalvar.setVisible(false);
        btAdicionar.setVisible(false);
        btAlterar.setVisible(false);
        btExcluir.setVisible(false);
        btCancelar.setVisible(false);
        btSalvarHTML.setVisible(false);
        pnCentro.setLayout(new GridLayout(colunas.length - 1, 2));
        pnCentro.add(lbNome_campus);
        pnCentro.add(tfNome_campus);
        pnCentro.add(lbTelefone);
        pnCentro.add(tfTelefone);
        pnCentro.add(lbUniversidade_id_universidade);
        pnCentro.add(cbUniversidade_id_universidade);
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

        btSalvarHTML.addActionListener(e -> {
            String html = null;
            DiretorioDaAplicacao da = new DiretorioDaAplicacao();
            try {
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecampus\\tabela_campus.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(CampusGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAOCampus daocampus = new DAOCampus();
            List<Campus> listacampus = daocampus.list();
            String json = "[";
            for (Campus campus : listacampus) {
                json += "{";
                json += "\"address_cep\":\"" + campus.getAddress_cep() + "\",";
                json += "\"nome_campus\":\"" + campus.getNome_campus() + "\",";
                json += "\"telefone\":\"" + campus.getTelefone() + "\",";
                json += "\"universidade_id_universidade\":\"" + campus.getUniversidade_id_universidade().toString().split(";")[1] + "\"},";
            }
            json += "]";
            html = html.replace("jsonPessoas", json);
            try {
                Files.write(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\siteaddress\\tabela_populada_address.html"), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(CidadeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //Abre o navegador Chrome com a URL especificada
                String url = ("file:///" + da.getDiretorioDaAplicacao() + "/src/HTML/siteaddress/tabela_populada_address.html").replace("\\", "/");
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
                valor = Integer.parseInt(tfAddress_cep.getText());
                if (campus != null) {
                    campus = controle.obter(valor);
                    if (campus != null) {
                        btAdicionar.setVisible(false);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btSalvarHTML.setVisible(false);
                        tfNome_campus.setText(String.valueOf(campus.getNome_campus()));
                        tfNome_campus.setEditable(false);
                        tfTelefone.setText(String.valueOf(campus.getTelefone()));
                        tfTelefone.setEditable(false);
                        cbUniversidade_id_universidade.setSelectedItem((campus.getUniversidade_id_universidade().toString().split(";")[0]));
                        cbUniversidade_id_universidade.setEnabled(false);
                    } else {// não achou na lista
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        btSalvarHTML.setVisible(false);
                    }
                }
            } catch (NumberFormatException ex1) {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
                tfAddress_cep.selectAll();
                tfAddress_cep.requestFocus();
            }
        });
        btAdicionar.addActionListener(e -> {
            tfAddress_cep.setEditable(false);
            tfAddress_cep.requestFocus();
            tfNome_campus.setEditable(true);
            tfTelefone.setEditable(true);
            cbUniversidade_id_universidade.setEnabled(true);
            btAdicionar.setVisible(false);
            btSalvar.setVisible(true);
            btCancelar.setVisible(true);
            btBuscar.setVisible(false);
            btListar.setVisible(false);
            btSalvarHTML.setVisible(false);
            acao = "adicionar";
        });
        btSalvar.addActionListener(e -> {
            if (acao.equals("adicionar")) {
                campus = new Campus();
            }
            if (!tfNome_campus.getText().equals("")) {
                if (!tfTelefone.getText().equals("")) {
                    campus.setAddress_cep(Integer.valueOf(tfAddress_cep.getText()));
                    tfAddress_cep.setText("");
                    tfAddress_cep.setEditable(false);
                    campus.setNome_campus(tfNome_campus.getText());
                    tfNome_campus.setText("");
                    tfNome_campus.setEditable(false);
                    campus.setTelefone(tfTelefone.getText());
                    tfTelefone.setText("");
                    tfTelefone.setEditable(false);
                    Universidade universidadeescolhida = new Universidade();
                    for (Universidade estado : listaUniversidades) {
                        String aux[] = estado.toString().split(";");
                        if (String.valueOf(cbUniversidade_id_universidade.getSelectedItem()).equals(aux[1])) {
                            universidadeescolhida = estado;
                        }
                    }
                    campus.setUniversidade_id_universidade(universidadeescolhida);
                    cbUniversidade_id_universidade.setEnabled(false);
                    btSalvar.setVisible(false);
                    btCancelar.setVisible(false);
                    btBuscar.setVisible(true);
                    btListar.setVisible(true);
                    btSalvarHTML.setVisible(false);
                    tfAddress_cep.setEnabled(true);
                    tfAddress_cep.setEditable(true);
                    tfAddress_cep.requestFocus();
                    tfAddress_cep.setText("");
                    tfAddress_cep.setText("");
                    if (acao.equals("adicionar")) {
                        controle.inserir(campus);
                    } else {
                        controle.atualizar(campus);
                    }
                } else {
                    JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (telefone)", "Erro ao adicionar",
                            JOptionPane.PLAIN_MESSAGE);
                    tfTelefone.selectAll();
                    tfTelefone.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (nome_campus)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
                tfNome_campus.selectAll();
                tfNome_campus.requestFocus();
            }
        });
        btAlterar.addActionListener(e -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarHTML.setVisible(false);
            tfAddress_cep.setEditable(false);
            tfNome_campus.setEditable(true);
            tfTelefone.setEditable(true);
            cbUniversidade_id_universidade.setEnabled(true);
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
            tfAddress_cep.setEnabled(true);
            tfAddress_cep.setEditable(true);
            tfAddress_cep.requestFocus();
            tfAddress_cep.setText("");
            tfNome_campus.setText("");
            tfNome_campus.setEditable(false);
            tfTelefone.setText("");
            tfTelefone.setEditable(false);
            cbUniversidade_id_universidade.setEnabled(false);
            btAlterar.setVisible(false);
            btSalvarHTML.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(campus);
            }
        });
        btListar.addActionListener(e -> {
            List<Campus> listaCampus = controle.list();
            String[] colunas = new String[]{"address_cep", "nome_campus", "telefone",
                "universidade_id_universidade"};
            String[][] dados = new String[listaCampus.size()][colunas.length];
            String aux[];
            for (int i = 0; i < listaCampus.size(); i++) {
                aux = listaCampus.get(i).toString().split(";");
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
            btSalvarHTML.setVisible(true);
        });
        btCancelar.addActionListener(e -> {
            btCancelar.setVisible(false);
            tfAddress_cep.setText("");
            tfAddress_cep.requestFocus();
            tfAddress_cep.setEnabled(true);
            tfAddress_cep.setEditable(true);
            tfNome_campus.setText("");
            tfNome_campus.setEditable(false);
            tfTelefone.setText("");
            tfTelefone.setEditable(false);
            cbUniversidade_id_universidade.setEnabled(false);
            btBuscar.setVisible(true);
            btListar.setVisible(true);
            btSalvar.setVisible(false);
            btCancelar.setVisible(false);
            btSalvarHTML.setVisible(false);

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
