package controller;

import java.sql.SQLException;
import java.util.List;
import model.Editora.EditoraBean;
import model.Editora.EditoraDAO;

public class EditoraController {
    private EditoraDAO editoras;

    public EditoraController(EditoraDAO editoraDAO) {
        this.editoras = editoraDAO;
    }

    public void adicionarEditora(String razaoSocial) throws SQLException {
        EditoraBean editoraBean = new EditoraBean();
        editoraBean.setRazaoSocial(razaoSocial);
        editoraBean.setStatus(true);

        editoras.adicionarEditora(editoraBean);
    }

    public void editarEditora(EditoraDAO editora) throws SQLException {
        EditoraBean editoraBean = new EditoraBean();
        editoraBean.setRazaoSocial(editora.razaoSocial);
        editoraBean.setStatus(true);

        editoras.atualizarEditora(editoraBean);
    }

    public void apagarEditora(int id) throws SQLException {
        editoras.excluirEditora(id);
    }

    public List<EditoraBean> listarEditoras() throws SQLException {
        return editoras.listarEditoras();
    }

    public void editarEditora(int id, String razaoSocial) {
    }
}
