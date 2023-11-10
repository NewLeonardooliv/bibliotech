package controller;

import java.sql.SQLException;
import java.util.List;

import model.Livros.LivrosBean;
import model.Livros.LivrosDAO;
import shared.ValidateException;

public class LivrosController {
    private LivrosDAO livrosDao;

    public LivrosController(LivrosDAO livrosDao) {
        this.livrosDao = livrosDao;
    }

    public void adicionar(String titulo, int autorId, int editoraId) throws SQLException, ValidateException {
        LivrosBean autoresBean = new LivrosBean();
        autoresBean.setTitulo(titulo);
        autoresBean.setAutorId(autorId);
        autoresBean.setEditoraId(editoraId);
        autoresBean.setStatus(true);

        livrosDao.adicionar(autoresBean);
    }

    public void editar(int livroId, String titulo, int autorId, int editoraId) throws SQLException, ValidateException {
        LivrosBean autoresBean = new LivrosBean();
        autoresBean.setId(livroId);
        autoresBean.setTitulo(titulo);
        autoresBean.setAutorId(autorId);
        autoresBean.setEditoraId(editoraId);
        autoresBean.setStatus(true);

        livrosDao.atualizar(autoresBean);
    }

    public void inativar(int id, boolean status) throws SQLException {
        livrosDao.inativar(id, status);
    }

    public List<LivrosBean> listar() throws SQLException, ValidateException {
        return livrosDao.listar();
    }

    public List<LivrosBean> pesquisar(String searchTerm, boolean showInactives) throws SQLException, ValidateException {
        return livrosDao.pesquisar(searchTerm, showInactives);
    }

    public LivrosBean obter(int livroId) throws SQLException, ValidateException {
        return livrosDao.obter(livroId);
    }
}
