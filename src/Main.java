import view.AmigosView;

import java.util.List;

import controller.AmigosController;
import model.Amigos.AmigosBean;
import model.Amigos.AmigosDAO;

public class Main {
    public static void main(String[] args) {
        AmigosDAO amigosDAO = new AmigosDAO();
        AmigosView amigosView = new AmigosView();
        AmigosController amigosController = new AmigosController(amigosDAO);

        amigosController.adicionarAmigo("Leonardo", "12312312312");
        List<AmigosBean> amigos = amigosController.buscarTodosAmigos();
        amigosView.exibirAmigos(amigos);
    }
}
