package model.Livros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.Database;
import shared.ValidateException;

public class LivrosDAO {
    private Connection connection;
    public String razaoSocial;

    public LivrosDAO() {
        this.connection = Database.connect();
    }

    public void adicionar(LivrosBean autor) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, editora, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, autor.getTitulo());
            ps.setInt(2, autor.getAutorId());
            ps.setInt(3, autor.getEditoraId());
            ps.setBoolean(4, autor.getStatus());
            ps.executeUpdate();
        }
    }

    public List<LivrosBean> listar() throws SQLException, ValidateException {
        List<LivrosBean> autores = new ArrayList<>();
        String sql = "SELECT id, titulo, editora, autor, status FROM livros";
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LivrosBean autor = new LivrosBean();
                autor.setId(rs.getInt("id"));
                autor.setTitulo(rs.getString("titulo"));
                autor.setAutorId(rs.getInt("autor"));
                autor.setEditoraId(rs.getInt("editora"));
                autor.setStatus(rs.getBoolean("status"));
                autores.add(autor);
            }
        }

        return autores;
    }

    public List<LivrosBean> pesquisar(String searchTerm) throws SQLException, ValidateException {
        List<LivrosBean> autores = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, editora, status FROM livros WHERE titulo LIKE ? OR id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, searchTerm);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LivrosBean autore = new LivrosBean();
                autore.setId(rs.getInt("id"));
                autore.setTitulo(rs.getString("razao_social"));
                autore.setAutorId(rs.getInt("autor"));
                autore.setEditoraId(rs.getInt("editora"));
                autore.setStatus(rs.getBoolean("status"));
                autores.add(autore);
            }
        }

        return autores;
    }

    public void atualizar(LivrosBean autore) throws SQLException {
        String sql = "UPDATE autores SET titulo = ?, editora = ?, autor = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, autore.getTitulo());
            ps.setInt(2, autore.getEditoraId());
            ps.setInt(3, autore.getAutorId());
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
        String sql = "UPDATE livros SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, autoreId);
            ps.executeUpdate();
        }
    }
}
