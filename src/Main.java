import javax.swing.SwingUtilities;

import view.HomeView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeView();
        });
    }
}
