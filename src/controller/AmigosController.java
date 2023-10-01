package controller;

import java.util.List;

import model.Amigos.AmigosBean;
import model.Amigos.AmigosDAO;

public class AmigosController {
    private AmigosDAO clienteDAO;

    public AmigosController(AmigosDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void adicionarAmigo(String nome, String email) {
        AmigosBean cliente = new AmigosBean();
        cliente.setNome(nome);
        cliente.setDocumento(email);
        cliente.setStatus("ATIVO");
        clienteDAO.adicionarAmigo(cliente);
    }

    public List<AmigosBean> buscarTodosClientes() {
        return clienteDAO.listarAmigos();
    }
}
