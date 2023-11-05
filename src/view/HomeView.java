package view;

import javax.swing.*;

import controller.EditoraController;
import model.Editora.EditoraDAO;
import view.components.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public HomeView() {
        setTitle("Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gerenciamento");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton editoraButton = new Button().get("Editoras");
        editoraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EditoraView(new EditoraController(new EditoraDAO()));
            }
        });

        homePanel.add(welcomeLabel, BorderLayout.CENTER);
        homePanel.add(editoraButton, BorderLayout.SOUTH);

        cardPanel.add(homePanel, "homeCard");

        add(cardPanel);

        cardLayout.show(cardPanel, "homeCard");

        setVisible(true);
    }

}
