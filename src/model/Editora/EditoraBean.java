package model.Editora;

import java.io.Serializable;

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
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
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
