package model.Editora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.Database;
import shared.ValidateException;

public class EditoraDAO {
    private Connection connection;
    public String razaoSocial;

    public EditoraDAO() {
        this.connection = Database.connect();
    }

    public void adicionarEditora(EditoraBean editora) throws SQLException {
        String sql = "INSERT INTO editoras (razao_social, status) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, editora.getRazaoSocial());
            ps.setBoolean(2, editora.getStatus());
            ps.executeUpdate();
        }
    }

    public List<EditoraBean> listarEditoras() throws SQLException, ValidateException {
        List<EditoraBean> editoras = new ArrayList<>();
        String sql = "SELECT id, razao_social, status FROM editoras WHERE status = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EditoraBean editora = new EditoraBean();
                editora.setId(rs.getInt("id"));
                editora.setRazaoSocial(rs.getString("razao_social"));
                editora.setStatus(rs.getBoolean("status"));
                editoras.add(editora);
            }
        }
        return editoras;
    }

    public List<EditoraBean> pesquisarEditoras(String searchTerm, boolean showInactives)
            throws SQLException, ValidateException {
        List<EditoraBean> editoras = new ArrayList<>();
        String sql = "SELECT id, razao_social, status FROM editoras WHERE (razao_social LIKE ? OR id = ?) AND status = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            ps.setString(2, searchTerm);
            ps.setBoolean(3, !showInactives);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EditoraBean editora = new EditoraBean();
                editora.setId(rs.getInt("id"));
                editora.setRazaoSocial(rs.getString("razao_social"));
                editora.setStatus(rs.getBoolean("status"));
                editoras.add(editora);
            }
        }

        return editoras;
    }

    public void atualizarEditora(EditoraBean editora) throws SQLException {
        String sql = "UPDATE editoras SET razao_social = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, editora.getRazaoSocial());
            ps.setBoolean(2, editora.getStatus());
            ps.setInt(3, editora.getId());
            ps.executeUpdate();
        }
    }

    public void excluirEditora(int id) throws SQLException {
        String sql = "DELETE FROM editoras WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void inativarAtivar(int editoraId, boolean status) throws SQLException {
        String sql = "UPDATE editoras SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, editoraId);
            ps.executeUpdate();
        }
    }

    public EditoraBean obter(int editoraId) throws SQLException, ValidateException {
        EditoraBean editora = new EditoraBean();
        String sql = "SELECT id, razao_social, status FROM editoras WHERE id = ? LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, editoraId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    editora = new EditoraBean();
                    editora.setId(rs.getInt("id"));
                    editora.setRazaoSocial(rs.getString("razao_social"));
                    editora.setStatus(rs.getBoolean("status"));
                }
            }
        }

        return editora;
    }
}
