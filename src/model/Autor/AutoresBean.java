package model.Autor;

import java.io.Serializable;

import shared.ValidateException;

public class AutoresBean implements Serializable {
    private int id;
    private String nome;
    private String documento;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public AutoresBean setNome(String nome) throws ValidateException {
        if (nome.length() < 4) {
            throw new ValidateException("Nome muito pequeno");
        }

        this.nome = nome;

        return this;
    }

    public String getDocumento() {
        return this.documento;
    }

    public AutoresBean setDocumento(String documento) {
        this.documento = documento;

        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
