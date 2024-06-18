package GUIs;

import DAOs.DAOCurso;
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
import Entidades.Curso;
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import tools.DiretorioDaAplicacao;

public class CursoGUI extends JDialog {

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
    JLabel lbId_Curso = new JLabel("Id_curso");
    JTextField tfId_curso = new JTextField(50);
    JLabel lbNome_curso = new JLabel("Nome_curso");
    JTextField tfNome_curso = new JTextField(50);
    DAOCurso controle = new DAOCurso();
    Curso curso = new Curso();
    String[] colunas = new String[]{"id_curso", "nome_curso"};
    String[][] dados = new String[0][colunas.length];

    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);

    public CursoGUI() {
        for (JButton button : Arrays.asList(btAdicionar, btBuscar, btSalvar, btAlterar, btExcluir, btListar, btCancelar, btSalvarPDF)) {
            button.setBackground(new Color(149, 127, 239));
            button.setFont(new Font("Poppins", Font.BOLD, 13));
            button.setForeground(Color.white);
        }
        for (JLabel jLabel : Arrays.asList(lbNome_curso, lbId_Curso)) {
            jLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            jLabel.setFont(new Font("Poppins", Font.CENTER_BASELINE, 12));
            jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, 25));
        }
        getContentPane().setBackground(new Color(183, 156, 237));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Curso");
        cp.add(pnNorte, BorderLayout.NORTH);
        cp.add(pnCentro, BorderLayout.CENTER);
        cp.add(pnSul, BorderLayout.SOUTH);
        cp.add(pnLeste, BorderLayout.EAST);
        pnLeste.add(pnLesteA, BorderLayout.NORTH);
        pnLeste.add(pnLesteB, BorderLayout.CENTER);

        pnCentro.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnNorte.add(lbId_Curso);
        pnNorte.add(tfId_curso);
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
        pnCentro.add(lbNome_curso);
        pnCentro.add(tfNome_curso);
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
                html = new String(Files.readAllBytes(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecurso\\tabela_curso.html")), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                Logger.getLogger(CursoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            DAOCurso daocurso = new DAOCurso();
            String jsonCurso = new Gson().toJson(daocurso.list());
            html = html.replace("jsonPessoas", jsonCurso);
            try {
                Files.write(Paths.get(da.getDiretorioDaAplicacao() + "\\src\\HTML\\sitecurso\\tabela_populada_curso.html"), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(CursoGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                // Abre o navegador Chrome com a URL especificada
                String url = ("file:///" + da.getDiretorioDaAplicacao() + "/src/HTML/sitecurso/tabela_populada_cursos.html").replace("\\", "/");
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
            }
        });
        // listener Buscar

        btBuscar.addActionListener(e
                -> {

            cardLayout.show(pnSul, "avisos");
            int valor;
            try {
                valor = Integer.parseInt(tfId_curso.getText().trim());
                if (curso != null) {
                    curso = controle.obter(valor);
                    if (curso != null) {
                        btAdicionar.setVisible(false);
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        btSalvarPDF.setVisible(false);
                        tfNome_curso.setText(String.valueOf(curso.getNomeCurso()));
                        tfNome_curso.setEditable(false);
                    } else {// não achou na lista
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        btSalvarPDF.setVisible(false);
                    }
                }
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados", "Erro ao buscar", JOptionPane.PLAIN_MESSAGE);
                tfId_curso.selectAll();
                tfId_curso.requestFocus();
            }
        }
        );
        btAdicionar.addActionListener(e
                -> {
            tfId_curso.setEditable(false);
            tfId_curso.requestFocus();
            tfNome_curso.setEditable(true);
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
                curso = new Curso();
            }
            if (!tfNome_curso.getText().equals("")) {
                curso.setIdCurso(Integer.valueOf(tfId_curso.getText()));
                tfId_curso.setText("");
                tfId_curso.setEditable(false);
                curso.setNomeCurso(tfNome_curso.getText());
                tfNome_curso.setText("");
                tfNome_curso.setEditable(false);
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btSalvarPDF.setVisible(false);
                tfId_curso.setEnabled(true);
                tfId_curso.setEditable(true);
                tfId_curso.requestFocus();
                tfId_curso.setText("");
                tfId_curso.setText("");
                System.out.println("Curso adicionado: " + curso.toString());
                if (acao.equals("adicionar")) {
                    controle.inserir(curso);
                } else {
                    controle.atualizar(curso);
                }
            } else {
                JOptionPane.showMessageDialog(cp, "Erro no tipo de dados (nome_curso)", "Erro ao adicionar",
                        JOptionPane.PLAIN_MESSAGE);
                tfNome_curso.selectAll();
                tfNome_curso.requestFocus();
            }
        }
        );
        btAlterar.addActionListener(e
                -> {
            btBuscar.setVisible(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            tfId_curso.setEditable(false);
            tfNome_curso.setEditable(true);
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
            tfId_curso.setEnabled(true);
            tfId_curso.setEditable(true);
            tfId_curso.requestFocus();
            tfId_curso.setText("");
            tfNome_curso.setText("");
            tfNome_curso.setEditable(false);
            btAlterar.setVisible(false);
            btSalvarPDF.setVisible(false);
            if (response == JOptionPane.YES_OPTION) {
                controle.remover(curso);
            }
        }
        );
        btListar.addActionListener((ActionEvent e) -> {
            List<Curso> listaCurso = controle.list();
            String[] colunas = new String[]{"id_curso", "nome_curso"};
            String[][] dados = new String[listaCurso.size()][colunas.length];
            String aux[];
            for (int i = 0; i < listaCurso.size(); i++) {
                aux = listaCurso.get(i).toString().split(";");
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
            tfNome_curso.setText("");

        }
        );
        btCancelar.addActionListener(e
                -> {
            btCancelar.setVisible(false);
            tfId_curso.setText("");
            tfId_curso.requestFocus();
            tfId_curso.setEnabled(true);
            tfId_curso.setEditable(true);
            tfNome_curso.setText("");
            tfNome_curso.setEditable(false);
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
