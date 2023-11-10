package controller;

import java.sql.SQLException;
import java.util.List;
import model.Editora.EditoraBean;
import model.Editora.EditoraDAO;
import shared.ValidateException;

public class EditoraController {
    private EditoraDAO editoras;

    public EditoraController(EditoraDAO editoraDAO) {
        this.editoras = editoraDAO;
    }

    public void adicionarEditora(String razaoSocial) throws SQLException, ValidateException {
        EditoraBean editoraBean = new EditoraBean();
        editoraBean.setRazaoSocial(razaoSocial);
        editoraBean.setStatus(true);

        editoras.adicionarEditora(editoraBean);
    }

    public void editarEditora(EditoraDAO editora) throws SQLException, ValidateException {
        EditoraBean editoraBean = new EditoraBean();
        editoraBean.setRazaoSocial(editora.razaoSocial);
        editoraBean.setStatus(true);

        editoras.atualizarEditora(editoraBean);
    }

    public void inativarAtivar(int id, boolean status) throws SQLException {
        editoras.inativarAtivar(id, status);
    }

    public List<EditoraBean> listarEditoras() throws SQLException, ValidateException {
        return editoras.listarEditoras();
    }

    public EditoraBean buscar(int editoraId) throws SQLException, ValidateException {
        return editoras.obter(editoraId);
    }

    public List<EditoraBean> pesquisarEditoras(String searchTerm, boolean showInactives)
            throws SQLException, ValidateException {
        return editoras.pesquisarEditoras(searchTerm, showInactives);
    }

    public void editarEditora(int id, String razaoSocial) throws SQLException, ValidateException {
        EditoraBean editoraBean = new EditoraBean();
        editoraBean.setId(id);
        editoraBean.setRazaoSocial(razaoSocial);
        editoraBean.setStatus(true);

        editoras.atualizarEditora(editoraBean);
    }
}
