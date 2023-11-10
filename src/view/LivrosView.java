package view;

import javax.swing.*;
import javax.swing.table.*;

import controller.AutoresController;
import controller.EditoraController;
import controller.LivrosController;
import model.Autor.AutoresDAO;
import model.Editora.EditoraBean;
import model.Editora.EditoraDAO;
import model.Livros.LivrosBean;
import shared.ValidateException;
import view.components.Button;
import model.Autor.AutoresBean;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LivrosView extends JFrame {
    private LivrosController controller;
    private JTable editoraTable;
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JCheckBox showInactiveCheckBox;

    public LivrosView(LivrosController controller) {
        this.controller = controller;

        setTitle("Livros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameX = (screenSize.width - getWidth()) / 2;
        int frameY = (screenSize.height - getHeight()) / 2;
        setLocation(frameX, frameY);

        String[] columnNames = { "Código", "Titulo", "Autor", "Editora" };
        tableModel = new DefaultTableModel(columnNames, 0);
        editoraTable = new JTable(tableModel);

        refreshTable();

        JButton addButton = new Button().setBackgroundColor(Button.GREEN).get("Adicionar");
        addButton.addActionListener(e -> createAction());

        JButton editButton = new Button().setBackgroundColor(Button.BLUE).get("Editar");
        editButton.addActionListener(e -> editAction(e));

        JButton deleteButton = new Button().setBackgroundColor(Button.RED).get("Excluir");
        deleteButton.addActionListener(e -> deleteAction(e));

        JButton backButton = new Button().setBackgroundColor(Button.CYAN).get("Voltar");
        backButton.addActionListener(e -> backAction());

        searchField = new JTextField(20);
        searchField.addActionListener(e -> searchAction());

        searchField.addKeyListener((KeyListener) new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchAction();
                }
            }
        });

        JPanel buttonActions = new JPanel();
        buttonActions.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonActions.add(addButton);
        buttonActions.add(editButton);
        buttonActions.add(deleteButton);

        JPanel southPanel = new JPanel(new BorderLayout());

        JPanel buttonBack = new JPanel();
        buttonBack.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonBack.add(backButton);

        southPanel.add(buttonActions, BorderLayout.WEST);
        southPanel.add(buttonBack, BorderLayout.EAST);

        add(new JScrollPane(editoraTable), BorderLayout.CENTER);
        add(new JLabel("Editoras"), BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Pesquisar:"));
        searchPanel.add(searchField);

        showInactiveCheckBox = new JCheckBox("Mostrar Excluídos");
        showInactiveCheckBox.addActionListener(e -> searchAction());
        searchPanel.add(showInactiveCheckBox);

        add(searchPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) editoraTable.getModel();
        tableModel.setRowCount(0);

        EditoraDAO editoraDAO = new EditoraDAO();
        AutoresDAO autoresDAO = new AutoresDAO();

        try {
            for (LivrosBean livros : controller.listar()) {

                EditoraBean editora = editoraDAO.obter(livros.getEditoraId());
                AutoresBean autor = autoresDAO.obter(livros.getAutorId());

                tableModel.addRow(new Object[] { livros.getId(), livros.getTitulo(), autor.getNome(),
                        editora.getRazaoSocial() });
            }
        } catch (SQLException | ValidateException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao atualizar a lista de editoras: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

        editoraTable.setRowHeight(25);
        editoraTable.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = editoraTable.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value != null && value.equals("Ativo")) {
                    setForeground(Color.GREEN);
                    return this;

                }
                if (value != null && value.equals("Excluído")) {
                    setForeground(Color.RED);
                    return this;

                }

                setForeground(Color.BLACK);

                return this;
            }
        };

        editoraTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
    }

    private void backAction() {
        dispose();
        new HomeView().setVisible(true);
    }

    private void editAction(ActionEvent e) {
        int selectedRow = editoraTable.getSelectedRow();

        if (selectedRow != -1) {
            int id = (int) editoraTable.getValueAt(selectedRow, 0);
            String nome = (String) editoraTable.getValueAt(selectedRow, 1);

            JDialog editDialog = new JDialog(frame, "Editar Editora", true);
            editDialog.setLayout(new GridLayout(4, 2, 5, 5));

            JTextField nomeEditField = new JTextField(nome, 20);
            JComboBox<AutoresBean> autorComboBox = new JComboBox<>();
            JComboBox<EditoraBean> editoraComboBox = new JComboBox<>();

            try {
                List<AutoresBean> autores = new AutoresController(new AutoresDAO()).listar();
                for (AutoresBean autor : autores) {
                    if (autor.getStatus()) {
                        autorComboBox.addItem(autor);
                    }
                }

                List<EditoraBean> editores = new EditoraController(new EditoraDAO()).listarEditoras();
                for (EditoraBean editora : editores) {
                    if (editora.getStatus()) {
                        editoraComboBox.addItem(editora);
                    }
                }
            } catch (SQLException | ValidateException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao exibir editora: " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }

            JButton saveButton = new Button().setBackgroundColor(Button.GREEN).get("Salvar");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String updatedTitulo = nomeEditField.getText();
                    AutoresBean autorSelecionado = (AutoresBean) autorComboBox.getSelectedItem();
                    EditoraBean editoraSelecionada = (EditoraBean) editoraComboBox.getSelectedItem();

                    try {
                        int autorId = autorSelecionado.getId();
                        int editoraId = editoraSelecionada.getId();

                        controller.editar(id, updatedTitulo, autorId, editoraId);
                        refreshTable();
                        editDialog.dispose();
                    } catch (SQLException | ValidateException ex) {
                        JOptionPane.showMessageDialog(frame, "Erro ao editar a editora: " + ex.getMessage(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            editDialog.add(new JLabel("Nome:"));
            editDialog.add(nomeEditField);
            editDialog.add(new JLabel("Autor:"));
            editDialog.add(autorComboBox);
            editDialog.add(new JLabel("Editora:"));
            editDialog.add(editoraComboBox);
            editDialog.add(new JLabel(""));
            editDialog.add(saveButton);

            editDialog.setSize(300, 200);

            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - editDialog.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - editDialog.getHeight()) / 2);
            editDialog.setLocation(x, y);

            editDialog.setVisible(true);
        }
    }

    private void deleteAction(ActionEvent e) {
        int selectedRow = editoraTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) editoraTable.getValueAt(selectedRow, 0);
            try {
                controller.inativar(id);
                refreshTable();
            } catch (SQLException ex) {
            }
        }
    }

    private void searchAction() {
        String searchTerm = searchField.getText().trim();
        boolean showInactive = showInactiveCheckBox.isSelected();

        tableModel.setRowCount(0);

        EditoraDAO editoraDAO = new EditoraDAO();
        AutoresDAO autoresDAO = new AutoresDAO();

        try {
            for (LivrosBean livros : controller.pesquisar(searchTerm, showInactive)) {

                EditoraBean editora = editoraDAO.obter(livros.getEditoraId());
                AutoresBean autor = autoresDAO.obter(livros.getAutorId());

                tableModel.addRow(new Object[] { livros.getId(), livros.getTitulo(), autor.getNome(),
                        editora.getRazaoSocial() });
            }
        } catch (SQLException | ValidateException ex) {
            ex.printStackTrace();
        }
    }

    private void createAction() {
        JDialog createDialog = new JDialog(frame, "Criar Editora", true);
        createDialog.setLayout(new GridLayout(4, 2, 5, 5));

        JTextField tituloEditField = new JTextField(20);
        JComboBox<AutoresBean> autorComboBox = new JComboBox<>();
        JComboBox<EditoraBean> editoraComboBox = new JComboBox<>();

        try {
            List<AutoresBean> autores = new AutoresController(new AutoresDAO()).listar();
            for (AutoresBean autor : autores) {
                if (autor.getStatus()) {
                    autorComboBox.addItem(autor);
                }
            }

            List<EditoraBean> editores = new EditoraController(new EditoraDAO()).listarEditoras();
            for (EditoraBean editora : editores) {
                if (editora.getStatus()) {
                    editoraComboBox.addItem(editora);
                }
            }
        } catch (SQLException | ValidateException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao criar a editora: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

        JButton saveButton = new Button().setBackgroundColor(Button.GREEN).get("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloEditField.getText();
                AutoresBean autorSelecionado = (AutoresBean) autorComboBox.getSelectedItem();
                EditoraBean editoraSelecionada = (EditoraBean) editoraComboBox.getSelectedItem();

                try {
                    int autorId = autorSelecionado.getId();
                    int editoraId = editoraSelecionada.getId();

                    controller.adicionar(titulo, autorId, editoraId);
                    refreshTable();
                    createDialog.dispose();
                } catch (SQLException | ValidateException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao criar a editora: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createDialog.add(new JLabel("Nome:"));
        createDialog.add(tituloEditField);
        createDialog.add(new JLabel("Autor:"));
        createDialog.add(autorComboBox);
        createDialog.add(new JLabel("Editora:"));
        createDialog.add(editoraComboBox);
        createDialog.add(new JLabel(""));
        createDialog.add(saveButton);

        createDialog.setSize(300, 200);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - createDialog.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - createDialog.getHeight()) / 2);
        createDialog.setLocation(x, y);

        createDialog.setVisible(true);
    }

}
