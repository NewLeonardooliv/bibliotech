package view;

import java.util.List;

import model.Amigos.AmigosBean;

public class AmigosView {

    public void exibirClientes(List<AmigosBean> clientes) {
        for (AmigosBean cliente : clientes) {
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Documento: " + cliente.getDocumento());
            System.out.println("Status: " + cliente.getStatus());
            System.out.println("----------------------");
        }
    }
}
