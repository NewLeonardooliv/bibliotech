package model.Livros;

import java.io.Serializable;

import shared.ValidateException;

public class LivrosBean implements Serializable {
    private int id;
    private String titulo;
    private int editoraId;
    private int autorId;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public LivrosBean setTitulo(String titulo) throws ValidateException {
        if (titulo.length() < 4) {
            throw new ValidateException("Titulo muito pequeno");
        }

        this.titulo = titulo;

        return this;
    }

    public int getEditoraId() {
        return this.editoraId;
    }

    public LivrosBean setEditoraId(int editoraId) {
        this.editoraId = editoraId;

        return this;
    }

    public int getAutorId() {
        return this.autorId;
    }

    public LivrosBean setAutorId(int autorId) {
        this.autorId = autorId;

        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
