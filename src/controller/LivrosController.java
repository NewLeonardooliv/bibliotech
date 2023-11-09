package controller;

import java.sql.SQLException;
import java.util.List;

import model.Livros.LivrosBean;
import model.Livros.LivrosDAO;
import shared.ValidateException;

public class LivrosController {
    private LivrosDAO autoresDao;

    public LivrosController(LivrosDAO autoresDAO) {
        this.autoresDao = autoresDAO;
    }

    public void adicionar(String titulo, int autorId, int editoraId) throws SQLException, ValidateException {
        LivrosBean autoresBean = new LivrosBean();
        autoresBean.setTitulo(titulo);
        autoresBean.setAutorId(autorId);
        autoresBean.setEditoraId(editoraId);
        autoresBean.setStatus(true);

        autoresDao.adicionar(autoresBean);
    }

    public void editar(int livroId, String titulo, int autorId, int editoraId) throws SQLException, ValidateException {
        LivrosBean autoresBean = new LivrosBean();
        autoresBean.setId(livroId);
        autoresBean.setTitulo(titulo);
        autoresBean.setAutorId(autorId);
        autoresBean.setEditoraId(editoraId);
        autoresBean.setStatus(true);

        autoresDao.atualizar(autoresBean);
    }

    public void inativar(int id) throws SQLException {
        autoresDao.inativar(id);
    }

    public List<LivrosBean> listar() throws SQLException, ValidateException {
        return autoresDao.listar();
    }

    public List<LivrosBean> pesquisar(String searchTerm) throws SQLException, ValidateException {
        return autoresDao.pesquisar(searchTerm);
    }
}
