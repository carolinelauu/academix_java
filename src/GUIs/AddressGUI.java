package GUIs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.text.MaskFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.JFormattedTextField;
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

import DAOs.DAOAddress;
import DAOs.DAOCidade;
import Entidades.Address;
import Entidades.Cidade;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import tools.DiretorioDaAplicacao;

public class AddressGUI extends JDialog {
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
    
    DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
    JLabel lbCidade_id_cidade = new JLabel("  Cidade");
    JComboBox cbCidade_id_cidade = new JComboBox(comboBoxModel);
    String acao = "";
    private JScrollPane scrollTabela = new JScrollPane();

    private final JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
    private final JPanel pnVazio = new JPanel(new GridLayout(6, 1));

    private CardLayout cardLayout;
    MaskFormatter mask;
    
    JFormattedTextField tfCep = new JFormattedTextField();
    
    JLabel lbCep = new JLabel("Cep");
    
    JLabel lbLogradouro = new JLabel("  Logradouro");
    JTextField tfLogradouro = new JTextField(50);
    JLabel lbNumber = new JLabel("  Number");
    JTextField tfNumber = new JTextField(50);
    DAOAddress controle = new DAOAddress();
    Address address = new Address();
    String[] colunas = new String[] { "cep", "logradouro", "number", "cidade_id_cidade" };
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public AddressGUI() throws ParseException {
        for (JButton button : Arrays.asList(btAdicionar, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarPDF)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        this.mask = new MaskFormatter("#####-###");
        mask.setPlaceholderCharacter('_');
        tfCep.setFormatterFactory(new DefaultFormatterFactory(mask));
        DAOCidade daocidade = new DAOCidade();
        List<Cidade> listaCidades = daocidade.listInOrderNome();
        for (Cidade cidade : listaCidades) {
            String aux[] = cidade.toString().split(";");
            comboBoxModel.addElement(aux[1]);
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Address");

        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbCep);
        pnNorte.add(tfCep);
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
        pnCentro.add(lbLogradouro);
        pnCentro.add(tfLogradouro);
        pnCentro.add(lbNumber);
        pnCentro.add(tfNumber);
        pnCentro.add(lbCidade_id_cidade);
        pnCentro.add(cbCidade_id_cidade);
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
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\siteaddress\\tabela_address.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(CidadeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAOAddress daoaddress = new DAOAddress();
           List<Address> listaaddress = daoaddress.list();
            String json = "[";
            for (Address address: listaaddress) {
                json += "{";
                json += "\"cep\":\""+address.getCep()+"\",";
                json += "\"logradouro\":\""+address.getLogradouro()+"\",";
                json += "\"number\":\""+address.getNumber()+"\",";
                json += "\"cidade_id_cidade\":\""+address.getCidade_id_cidade().toString().split(";")[1]+"\"},";
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
            String cep = tfCep.getText().replace("-", "");
            
            if(cep.length() == 8) {
                if (address != null) {
                    address = controle.obter(Integer.valueOf(cep));
                    if (address != null) {
                        btAdicionar.setVisible(false);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btSalvarPDF.setVisible(false);
                        tfLogradouro.setText(String.valueOf(address.getLogradouro()));
                        tfLogradouro.setEditable(false);
                        tfNumber.setText(String.valueOf(address.getNumber()));
                        tfNumber.setEditable(false);
                        cbCidade_id_cidade.setSelectedItem((address.getCidade_id_cidade().toString().split(";")[1]));
                        cbCidade_id_cidade.setEnabled(false);
                    } else {// não achou na lista
                        
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        btSalvarPDF.setVisible(false);
                        tfNumber.setText("");
                        tfNumber.setEditable(false);
                        cbCidade_id_cidade.setEnabled(false);
                    }
                }
            } else{
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
                tfCep.selectAll();
                tfCep.requestFocus();
            }
        });
        btAdicionar.addActionListener(e -> {
            tfCep.setEditable(false);
            tfCep.requestFocus();
            tfLogradouro.setEditable(true);
            tfNumber.setEditable(true);
            cbCidade_id_cidade.setEnabled(true);
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
                address = new Address();
            }
            if (!tfLogradouro.getText().equals("")) {
               
                    int valor2 = Integer.parseInt(tfNumber.getText());
                    try {
                        address.setCep(Integer.valueOf(tfCep.getText().replace("-", "")));
                        tfCep.setText("");
                        tfCep.setEditable(false);
                        address.setLogradouro(tfLogradouro.getText());
                        tfLogradouro.setText("");
                        tfLogradouro.setEditable(false);
                        address.setNumber(Integer.valueOf(tfNumber.getText()));
                        tfNumber.setText("");
                        tfNumber.setEditable(false);
                        Cidade cidadeescolhida = new Cidade();
                        for (Cidade cidade : listaCidades) {
                            String aux[] = cidade.toString().split(";");
                            if(String.valueOf(cbCidade_id_cidade.getSelectedItem()).equals(aux[1])){
                                cidadeescolhida = cidade;
                            }
                        }
                        address.setCidade_id_cidade(cidadeescolhida);
                        cbCidade_id_cidade.setEnabled(false);
                        btSalvar.setVisible(false);
                        btCancelar.setVisible(false);
                        btBuscar.setVisible(true);
                        btListar.setVisible(true);
                        btSalvarPDF.setVisible(false);
                        tfCep.setEnabled(true);
                        tfCep.setEditable(true);
                        tfCep.requestFocus();
                        tfCep.setText("");
                        tfCep.setText("");
                        if (acao.equals("adicionar")) {
                            controle.inserir(address);
                        } else {
                            controle.atualizar(address);
                        }
                } catch (NumberFormatException ex2) {
                    JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (number)", "Erro ao adicionar",
                            JOptionPane.PLAIN_MESSAGE);
                    tfNumber.selectAll();
                    tfNumber.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (logradouro)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
                tfLogradouro.selectAll();
                tfLogradouro.requestFocus();
            }
        });
        btAlterar.addActionListener(e -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            tfCep.setEditable(false);
            tfLogradouro.setEditable(true);
            tfNumber.setEditable(true);
            cbCidade_id_cidade.setEnabled(true);
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
            tfCep.setEnabled(true);
            tfCep.setEditable(true);
            tfCep.requestFocus();
            tfCep.setText("");
            tfLogradouro.setText("");
            tfLogradouro.setEditable(false);
            tfNumber.setText("");
            tfNumber.setEditable(false);
            cbCidade_id_cidade.setEnabled(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(address);
            }
        });
        btListar.addActionListener(e -> {
            List<Address> listaAddress = controle.list();
            String[] colunas = new String[] { "cep", "logradouro", "number", "cidade_id_cidade" };
            String[][] dados = new String[listaAddress.size()][colunas.length];
            String aux[];
            for (int i = 0; i < listaAddress.size(); i++) {
                aux = listaAddress.get(i).toString().split(";");
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
            tfCep.setText("");
            tfCep.requestFocus();
            tfCep.setEnabled(true);
            tfCep.setEditable(true);
            tfLogradouro.setText("");
            tfLogradouro.setEditable(false);
            tfNumber.setText("");
            tfNumber.setEditable(false);
            cbCidade_id_cidade.setEnabled(false);
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