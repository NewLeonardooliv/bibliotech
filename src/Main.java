import view.EditoraView;


import controller.EditoraController;
import model.Editora.EditoraDAO;

public class Main {
    public static void main(String[] args) {
        new EditoraView(
                new EditoraController(
                        new EditoraDAO()));
    }
}
