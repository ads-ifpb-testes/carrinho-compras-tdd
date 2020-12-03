package br.edu.ifpb.testes.carrinho.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    public static String url = "jdbc:postgresql://localhost:5432/exemplo-testes";
    public static String user = "postgres";
    public static String password = "123456";

    private ConexaoFactory() {}

    public static Connection getConnection() throws ConexaoException {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new ConexaoException("Driver não encontrado");
        } catch (SQLException e) {
            throw new ConexaoException("Falha ao conectar ao banco");
        }
        return connection;
    }
}
