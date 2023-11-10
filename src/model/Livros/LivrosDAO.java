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
        String sql = "SELECT id, titulo, editora, autor, status FROM livros WHERE status = 1";
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

    public List<LivrosBean> pesquisar(String searchTerm, boolean showInactives) throws SQLException, ValidateException {
        List<LivrosBean> autores = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, editora, status FROM livros WHERE (titulo LIKE ? OR id = ?) AND status = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, searchTerm);
            ps.setBoolean(3, !showInactives);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LivrosBean autore = new LivrosBean();
                autore.setId(rs.getInt("id"));
                autore.setTitulo(rs.getString("titulo"));
                autore.setAutorId(rs.getInt("autor"));
                autore.setEditoraId(rs.getInt("editora"));
                autore.setStatus(rs.getBoolean("status"));
                autores.add(autore);
            }
        }

        return autores;
    }

    public void atualizar(LivrosBean livro) throws SQLException {
        String sql = "UPDATE livros SET titulo = ?, editora = ?, autor = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getEditoraId());
            ps.setInt(3, livro.getAutorId());
            ps.setBoolean(4, livro.getStatus());
            ps.setInt(5, livro.getId());

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
        String sql = "UPDATE livros SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, autoreId);
            ps.executeUpdate();
        }
    }

    public LivrosBean obter(int autorId) throws SQLException, ValidateException {
        LivrosBean livro = new LivrosBean();
        String sql = "SELECT id, titulo, autor, editora, status FROM livros WHERE id = ? LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, autorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    livro = new LivrosBean();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setEditoraId(rs.getInt("editora"));
                    livro.setAutorId(rs.getInt("autor"));
                    livro.setStatus(rs.getBoolean("status"));
                }
            }
        }

        return livro;
    }
}
