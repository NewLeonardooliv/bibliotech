package controller;

import java.util.List;

import model.Amigos.AmigosBean;
import model.Amigos.AmigosDAO;

public class AmigosController {
    private AmigosDAO amigoDAO;

    public AmigosController(AmigosDAO amigoDAO) {
        this.amigoDAO = amigoDAO;
    }

    public void adicionarAmigo(String nome, String email) {
        AmigosBean amigo = new AmigosBean();
        amigo.setNome(nome);
        amigo.setDocumento(email);
        amigo.setStatus("ATIVO");
        amigoDAO.adicionarAmigo(amigo);
    }

    public List<AmigosBean> buscarTodosAmigos() {
        return amigoDAO.listarAmigos();
    }
}
