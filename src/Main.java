import view.AmigosView;

import java.util.List;

import controller.AmigosController;
import model.Amigos.AmigosBean;
import model.Amigos.AmigosDAO;

public class Main {
    public static void main(String[] args) {
        AmigosDAO clienteDAO = new AmigosDAO();
        AmigosView clienteView = new AmigosView();
        AmigosController clienteController = new AmigosController(clienteDAO);

        clienteController.adicionarAmigo("Leonardo", "leonardo@email.com");
        List<AmigosBean> clientes = clienteController.buscarTodosClientes();
        clienteView.exibirClientes(clientes);
    }
}
