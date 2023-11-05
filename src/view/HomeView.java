package view;

import javax.swing.*;

import controller.EditoraController;
import model.Editora.EditoraDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView {
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;

    public HomeView() {
        frame = new JFrame("Programa Inicial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Crie um painel de cartões para a tela inicial
        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);

        // Adicione um rótulo com o nome do programa à tela inicial
        JLabel nameLabel = new JLabel("Nome do Programa");
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        cards.add(nameLabel, "home");

        // Crie um botão para navegar para a tela de EditoraView
        JButton editoraButton = new JButton("Ir para EditoraView");
        editoraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "editora");
            }
        });

        // Adicione o botão à tela inicial
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.add(nameLabel, BorderLayout.CENTER);
        homePanel.add(editoraButton, BorderLayout.SOUTH);

        cards.add(homePanel, "home");

        // Crie uma instância de EditoraView e adicione-a aos cartões
        EditoraView editoraView = new EditoraView(
                new EditoraController(
                        new EditoraDAO()));
        cards.add(editoraView.get(), "editora");

        // Exiba a tela inicial
        frame.add(cards);
        cardLayout.show(cards, "home");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeView();
            }
        });
    }
}
