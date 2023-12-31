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
        String sql = "SELECT ID, nome, documento, status FROM autores WHERE status = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AutoresBean autor = new AutoresBean();
                autor.setId(rs.getInt("ID"));
                autor.setNome(rs.getString("nome"));
                autor.setDocumento(rs.getString("documento"));
                autor.setStatus(rs.getBoolean("status"));
                autores.add(autor);
            }
        }

        return autores;
    }

    public List<AutoresBean> pesquisar(String searchTerm, boolean showInactives)
            throws SQLException, ValidateException {
        List<AutoresBean> autores = new ArrayList<>();
        String sql = "SELECT id, nome, documento, status FROM autores WHERE (nome LIKE ? OR id = ?) AND status = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, searchTerm);
            ps.setBoolean(3, !showInactives);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AutoresBean autore = new AutoresBean();
                autore.setId(rs.getInt("id"));
                autore.setNome(rs.getString("nome"));
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

    public void inativar(int autoreId, boolean status) throws SQLException {
        String sql = "UPDATE autores SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, autoreId);
            ps.executeUpdate();
        }
    }

    public AutoresBean obter(int autorId) throws SQLException, ValidateException {
        AutoresBean autor = new AutoresBean();
        String sql = "SELECT id, nome, status FROM autores WHERE id = ? LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, autorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    autor = new AutoresBean();
                    autor.setId(rs.getInt("id"));
                    autor.setNome(rs.getString("nome"));
                    autor.setStatus(rs.getBoolean("status"));
                }
            }
        }

        return autor;
    }
}
