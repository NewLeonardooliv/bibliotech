package model.Editora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.Database;

public class EditoraDAO {
    private Connection connection;

    public EditoraDAO(Connection connection) {
        this.connection = Database.connect();
    }

    public void adicionarEditora(EditoraBean editora) throws SQLException {
        String sql = "INSERT INTO editora (razao_social, status) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, editora.getRazaoSocial());
            ps.setBoolean(2, editora.isStatus());
            ps.executeUpdate();
        }
    }

    public List<EditoraBean> listarEditoras() throws SQLException {
        List<EditoraBean> editoras = new ArrayList<>();
        String sql = "SELECT id, razao_social, status FROM editora";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EditoraBean editora = new EditoraBean();
                editora.setId(rs.getInt("id"));
                editora.setRazaoSocial(rs.getString("razaoSocial"));
                editora.setStatus(rs.getBoolean("status"));
                editoras.add(editora);
            }
        }
        return editoras;
    }

    public void atualizarEditora(EditoraBean editora) throws SQLException {
        String sql = "UPDATE editora SET razao_social = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, editora.getRazaoSocial());
            ps.setBoolean(2, editora.isStatus());
            ps.setInt(3, editora.getId());
            ps.executeUpdate();
        }
    }

    public void excluirEditora(int id) throws SQLException {
        String sql = "DELETE FROM editora WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

