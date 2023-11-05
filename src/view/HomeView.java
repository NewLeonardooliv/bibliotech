package view;

import javax.swing.*;

import controller.AutoresController;
import controller.EditoraController;
import model.Autor.AutoresDAO;
import model.Editora.EditoraDAO;

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

        JPanel homePanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gerenciamento");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Crie botões personalizados
        JButton editoraButton = createCustomButton("Editoras");
        JButton autoresButton = createCustomButton("Autores");
        JButton livrosButton = createCustomButton("Livros");
        JButton amigosButton = createCustomButton("Amigos");

        homePanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(editoraButton);
        buttonPanel.add(autoresButton);
        buttonPanel.add(livrosButton);
        buttonPanel.add(amigosButton);

        homePanel.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(homePanel, "homeCard");

        add(cardPanel);

        cardLayout.show(cardPanel, "homeCard");

        setVisible(true);
    }

    private JButton createCustomButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (buttonText.equals("Editoras")) {
                    new EditorasView(new EditoraController(new EditoraDAO()));
                } else if (buttonText.equals("Autores")) {
                    new AutoresView(new AutoresController(new AutoresDAO()));
                } else if (buttonText.equals("Livros")) {
                    // Substitua com a lógica para abrir a tela de Livros
                } else if (buttonText.equals("Amigo")) {
                    // Substitua com a lógica para abrir a tela de Livros
                }
            }
        });
        return button;
    }

}
