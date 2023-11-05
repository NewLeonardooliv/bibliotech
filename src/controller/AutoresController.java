package controller;

import java.sql.SQLException;
import java.util.List;

import model.Autor.AutoresBean;
import model.Autor.AutoresDAO;
import shared.ValidateException;

public class AutoresController {
    private AutoresDAO autoresDao;

    public AutoresController(AutoresDAO autoresDAO) {
        this.autoresDao = autoresDAO;
    }

    public void adicionar(String nome, String documento) throws SQLException, ValidateException {
        AutoresBean autoresBean = new AutoresBean();
        autoresBean.setNome(nome);
        autoresBean.setDocumento(documento);
        autoresBean.setStatus(true);

        autoresDao.adicionar(autoresBean);
    }

    public void editar(int autorId, String nome, String documento) throws SQLException, ValidateException {
        AutoresBean autoresBean = new AutoresBean();
        autoresBean.setId(autorId);
        autoresBean.setNome(nome);
        autoresBean.setDocumento(documento);
        autoresBean.setStatus(true);

        autoresDao.atualizar(autoresBean);
    }

    public void inativar(int id) throws SQLException {
        autoresDao.inativar(id);
    }

    public List<AutoresBean> listar() throws SQLException, ValidateException {
        return autoresDao.listar();
    }

    public List<AutoresBean> pesquisar(String searchTerm) throws SQLException, ValidateException {
        return autoresDao.pesquisar(searchTerm);
    }
}
