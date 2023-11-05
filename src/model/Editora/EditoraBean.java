package model.Editora;

import java.io.Serializable;

import shared.ValidateException;

public class EditoraBean implements Serializable {
    private int id;
    private String razaoSocial;
    private boolean status;

    public EditoraBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) throws ValidateException {
        if (razaoSocial.length() < 4) {
            throw new ValidateException("Razão social muito pequeno");
        }

        this.razaoSocial = razaoSocial;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EditoraBean [id=" + id + ", razaoSocial=" + razaoSocial + ", status=" + status + "]";
    }
}
