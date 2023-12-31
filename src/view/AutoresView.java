package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.AutoresController;
import model.Autor.AutoresBean;
import shared.ValidateException;
import view.components.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class AutoresView extends JFrame {
    private AutoresController controller;
    private JTable editoraTable;
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JCheckBox showInactiveCheckBox;

    public AutoresView(AutoresController controller) {
        this.controller = controller;

        setTitle("Autores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameX = (screenSize.width - getWidth()) / 2;
        int frameY = (screenSize.height - getHeight()) / 2;
        setLocation(frameX, frameY);

        String[] columnNames = { "Código", "Nome", "Documento" };
        tableModel = new DefaultTableModel(columnNames, 0);
        editoraTable = new JTable(tableModel);

        refreshTable();

        JButton addButton = new Button().setBackgroundColor(Button.GREEN).get("Adicionar");
        addButton.addActionListener(e -> createAction());

        JButton editButton = new Button().setBackgroundColor(Button.BLUE).get("Editar");
        editButton.addActionListener(e -> editAction(e));

        JButton deleteButton = new Button().setBackgroundColor(Button.ORANGE).get("Excluir/Recuperar");
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

        showInactiveCheckBox = new JCheckBox("Mostrar Excluídos");
        showInactiveCheckBox.addActionListener(e -> searchAction());

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
        searchPanel.add(showInactiveCheckBox);

        add(searchPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public void refreshTable() {
        DefaultTableModel tableModel = (DefaultTableModel) editoraTable.getModel();
        tableModel.setRowCount(0);

        try {
            for (AutoresBean editora : controller.listar()) {
                tableModel.addRow(new Object[] { editora.getId(), editora.getNome(), editora.getDocumento() });
            }
        } catch (SQLException | ValidateException ex) {
            ex.printStackTrace();
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
            String documento = (String) editoraTable.getValueAt(selectedRow, 2);

            JDialog editDialog = new JDialog(frame, "Editar Editora", true);
            editDialog.setLayout(new GridLayout(3, 2, 5, 5));

            JTextField nomeEditField = new JTextField(nome, 20);
            JTextField documentoEditField = new JTextField(documento, 20);
            JButton saveButton = new Button().setBackgroundColor(Button.GREEN).get("Salvar");

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String updatedNome = nomeEditField.getText();
                    String updatedDocumento = documentoEditField.getText();

                    try {
                        controller.editar(id, updatedNome, updatedDocumento);
                        refreshTable();
                        editDialog.dispose();
                    } catch (SQLException | ValidateException ex) {
                        JOptionPane.showMessageDialog(frame, "Erro ao editar a editora: " + ex.getMessage(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            try {
                AutoresBean autor = controller.obter(id);
                if (!autor.getStatus()) {
                    JOptionPane.showMessageDialog(frame, "O autor selecionado foi excluído e não pode ser editado.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException | ValidateException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao verificar o status do autor: " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            editDialog.add(new JLabel("Nome:"));
            editDialog.add(nomeEditField);
            editDialog.add(new JLabel("Documento:"));
            editDialog.add(documentoEditField);
            editDialog.add(new JLabel(""));
            editDialog.add(saveButton);

            editDialog.setSize(300, 150);

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
            int confirmDialogResult = JOptionPane.showConfirmDialog(
                    frame,
                    "Tem certeza de que deseja excluir/recuperar?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                try {
                    AutoresBean autor = controller.obter(id);
                    controller.inativar(id, !autor.getStatus());
                    refreshTable();
                } catch (SQLException | ValidateException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao excluir/recuperar: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void searchAction() {
        String searchTerm = searchField.getText().trim();
        boolean showInactive = showInactiveCheckBox.isSelected();

        tableModel.setRowCount(0);
        try {
            for (AutoresBean editora : controller.pesquisar(searchTerm, showInactive)) {
                tableModel.addRow(new Object[] { editora.getId(), editora.getNome(), editora.getDocumento() });
            }
        } catch (SQLException | ValidateException ex) {
            ex.printStackTrace();
        }
    }

    private void createAction() {
        JDialog createDialog = new JDialog(frame, "Criar Autor", true);
        createDialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        JTextField nomeEditField = new JTextField(20);
        JTextField documentoEditField = new JTextField(20);

        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nomeEditField);
        inputPanel.add(new JLabel("Documento:"));
        inputPanel.add(documentoEditField);
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new Button()
                .setBackgroundColor(Button.GREEN)
                .get("Salvar");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeEditField.getText();
                String documento = documentoEditField.getText();

                try {
                    controller.adicionar(nome, documento);
                    refreshTable();
                    createDialog.dispose();
                } catch (SQLException | ValidateException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao criar autor: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(saveButton);

        createDialog.add(inputPanel, BorderLayout.CENTER);
        createDialog.add(buttonPanel, BorderLayout.SOUTH);

        createDialog.setSize(300, 150);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - createDialog.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - createDialog.getHeight()) / 2);
        createDialog.setLocation(x, y);

        // Torna o diálogo visível
        createDialog.setVisible(true);
    }
}
