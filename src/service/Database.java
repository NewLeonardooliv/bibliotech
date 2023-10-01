package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection connect() {
        String jdbcUrl = "jdbc:mysql://localhost:3308/livro";
        String usuario = "root";
        String senha = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcUrl, usuario, senha);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
