package model.Amigos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import service.Database;

public class AmigosDAO {
    private Connection connection;

    public AmigosDAO() {
        // String jdbcUrl = "jdbc:mysql://localhost:3308/livro";
        // String usuario = "root";
        // String senha = "root";

        // try {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // this.connection = DriverManager.getConnection(jdbcUrl, usuario, senha);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        this.connection = Database.connect();
    }

    public void adicionarAmigo(AmigosBean amigo) {
        String sql = "INSERT INTO amigos (nome, documento, status) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, amigo.getNome());
            preparedStatement.setString(2, amigo.getDocumento());
            preparedStatement.setString(3, amigo.getStatus());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AmigosBean> listarAmigos() {
        List<AmigosBean> amigos = new ArrayList<>();
        String sql = "SELECT * FROM amigos";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AmigosBean amigo = new AmigosBean();
                amigo.setId(resultSet.getInt("id"));
                amigo.setNome(resultSet.getString("nome"));
                amigo.setDocumento(resultSet.getString("documento"));
                amigos.add(amigo);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return amigos;
    }
}
