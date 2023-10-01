package view;

import java.util.List;

import model.Amigos.AmigosBean;

public class AmigosView {

    public void exibirAmigos(List<AmigosBean> amigos) {
        for (AmigosBean amigo : amigos) {
            System.out.println("ID: " + amigo.getId());
            System.out.println("Nome: " + amigo.getNome());
            System.out.println("Documento: " + amigo.getDocumento());
            System.out.println("Status: " + amigo.getStatus());
            System.out.println("----------------------");
        }
    }
}
