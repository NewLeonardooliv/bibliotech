package view;

import controller.EditoraController;
import model.Editora.EditoraBean;
import shared.ValidateException;
import view.components.Button;

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
        refreshTable();
    }

    private void initialize() {
        String[] columnNames = { "ID", "Razão Social", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        editoraTable = new JTable(tableModel);

        razaoSocialField = new JTextField(20);
        razaoSocialField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton addButton = new Button().setBackgroundColor(Button.GREEN).get("Adicionar");
        addButton.addActionListener(e -> createAction(e));

        JButton editButton = new Button().setBackgroundColor(Button.BLUE).get("Editar");
        editButton.addActionListener(e -> editAction(e));

        JButton deleteButton = new Button().setBackgroundColor(Button.RED).get("Inativar");
        deleteButton.addActionListener(e -> deleteAction(e));

        JLabel razaoSocialLabel = new JLabel("Razão Social:");
        razaoSocialLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panel = this.initPanel();
        panel.add(razaoSocialLabel);
        panel.add(razaoSocialField);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        this.initFrame(panel);

    }

    private void initFrame(JPanel panel) {
        frame = new JFrame("Editoras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameX = (screenSize.width - frame.getWidth()) / 2;
        int frameY = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(frameX, frameY);

        JScrollPane tableScrollPane = new JScrollPane(editoraTable);

        frame.setLayout(new BorderLayout());
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel initPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        return panel;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        try {
            for (EditoraBean editora : controller.listarEditoras()) {
                tableModel.addRow(new Object[] { editora.getId(), editora.getRazaoSocial(),
                        editora.isStatus() ? "Ativo" : "Inativo" });
            }
        } catch (SQLException | ValidateException ex) {
            ex.printStackTrace();
        }
    }

    private void editAction(ActionEvent e) {
        int selectedRow = editoraTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) editoraTable.getValueAt(selectedRow, 0);
            String razaoSocial = (String) editoraTable.getValueAt(selectedRow, 1);

            JDialog editDialog = new JDialog(frame, "Editar Editora", true);
            editDialog.setLayout(new FlowLayout());

            JTextField razaoSocialEditField = new JTextField(razaoSocial);
            JButton saveButton = new JButton("Salvar");

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String updatedRazaoSocial = razaoSocialEditField.getText();
                    try {
                        controller.editarEditora(id, updatedRazaoSocial);
                        refreshTable();
                        editDialog.dispose();
                    } catch (SQLException | ValidateException ex) {
                        JOptionPane.showMessageDialog(frame, "Erro ao editar a editora: " + ex.getMessage(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            editDialog.add(new JLabel("Nova Razão Social:"));
            editDialog.add(razaoSocialEditField);
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
            try {
                controller.apagarEditora(id);
                refreshTable();
                razaoSocialField.setText("");
            } catch (SQLException ex) {
            }
        }
    }

    private void createAction(ActionEvent e) {
        String razaoSocial = razaoSocialField.getText();
        try {
            controller.adicionarEditora(razaoSocial);
            refreshTable();
            razaoSocialField.setText("");
        } catch (SQLException | ValidateException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao criar editora: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
