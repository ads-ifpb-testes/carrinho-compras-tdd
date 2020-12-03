package br.edu.ifpb.testes.carrinho.produto;

import br.edu.ifpb.testes.carrinho.conexao.ConexaoException;
import br.edu.ifpb.testes.carrinho.conexao.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private Connection conexao;

    public ProdutoDAOImpl() {
        try {
            this.conexao = ConexaoFactory.getConnection();
        } catch (ConexaoException e) {
            e.printStackTrace();
        }
    }

    public ProdutoDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void salvar(Produto p) throws SQLException {
        String query = "INSERT INTO produto(nome, preco) VALUES (?, ?)";
        PreparedStatement preparedStatement = this.conexao.prepareStatement(query);
        preparedStatement.setString(1, p.getNome());
        preparedStatement.setLong(2, p.getPreco());
        preparedStatement.execute();
    }

    @Override
    public List<Produto> listar() throws SQLException {
        String query = "SELECT * FROM produto";
        List<Produto> produtos = new LinkedList<>();
        PreparedStatement preparedStatement = conexao.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Produto produto = new Produto(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getLong(3)
            );
            produtos.add(produto);
        }
        return produtos;
    }

}
