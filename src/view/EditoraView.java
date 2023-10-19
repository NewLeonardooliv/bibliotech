package view;

import controller.EditoraController;
import model.Editora.EditoraBean;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditoraView {
    private JFrame frame;
    private JTable editoraTable;
    private DefaultTableModel tableModel;
    private JTextField razaoSocialField;
    private EditoraController controller;

    public EditoraView(EditoraController controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Editoras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        String[] columnNames = {"ID", "Razão Social"};
        tableModel = new DefaultTableModel(columnNames, 0);
        editoraTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(editoraTable);

        JLabel razaoSocialLabel = new JLabel("Razão Social:");
        razaoSocialField = new JTextField(20);

        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Apagar");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String razaoSocial = razaoSocialField.getText();
                controller.adicionarEditora(razaoSocial);
                refreshTable();
                razaoSocialField.setText("");
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = editoraTable.getSelectedRow();
                if (selectedRow != -1) {
                    String razaoSocial = razaoSocialField.getText();
                    int id = (int) editoraTable.getValueAt(selectedRow, 0);
                    controller.editarEditora(id, razaoSocial);
                    refreshTable();
                    razaoSocialField.setText("");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = editoraTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) editoraTable.getValueAt(selectedRow, 0);
                    controller.apagarEditora(id);
                    refreshTable();
                    razaoSocialField.setText("");
                }
            }
        });

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(razaoSocialLabel);
        panel.add(razaoSocialField);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        frame.setLayout(new BorderLayout());
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        try {
            for (EditoraBean editora : controller.listarEditoras()) {
                tableModel.addRow(new Object[]{editora.getId(), editora.getRazaoSocial()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

