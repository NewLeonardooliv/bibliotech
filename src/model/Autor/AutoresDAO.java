package model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.Database;
import shared.ValidateException;

public class AutoresDAO {
    private Connection connection;
    public String razaoSocial;

    public AutoresDAO() {
        this.connection = Database.connect();
    }

    public void adicionar(AutoresBean autor) throws SQLException {
        String sql = "INSERT INTO autores (nome, documento, status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, autor.getNome());
            ps.setString(2, autor.getDocumento());
            ps.setBoolean(3, autor.getStatus());
            ps.executeUpdate();
        }
    }

    public List<AutoresBean> listar() throws SQLException, ValidateException {
        List<AutoresBean> autores = new ArrayList<>();
        String sql = "SELECT id, nome, documento, status FROM autores";
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AutoresBean autor = new AutoresBean();
                autor.setId(rs.getInt("id"));
                autor.setNome(rs.getString("nome"));
                autor.setDocumento(rs.getString("documento"));
                autor.setStatus(rs.getBoolean("status"));
                autores.add(autor);
            }
        }

        return autores;
    }

    public List<AutoresBean> pesquisar(String searchTerm) throws SQLException, ValidateException {
        List<AutoresBean> autores = new ArrayList<>();
        String sql = "SELECT id, nome, documento, status FROM autores WHERE razao_social LIKE ? OR id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, searchTerm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AutoresBean autore = new AutoresBean();
                autore.setId(rs.getInt("id"));
                autore.setNome(rs.getString("razao_social"));
                autore.setDocumento(rs.getString("documento"));
                autore.setStatus(rs.getBoolean("status"));
                autores.add(autore);
            }
        }

        return autores;
    }

    public void atualizar(AutoresBean autore) throws SQLException {
        String sql = "UPDATE autores SET nome = ?, documento = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getDocumento());
            ps.setBoolean(3, autore.getStatus());
            ps.setInt(4, autore.getId());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM autores WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void inativar(int autoreId) throws SQLException {
        String sql = "UPDATE autores SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, autoreId);
            ps.executeUpdate();
        }
    }
}
