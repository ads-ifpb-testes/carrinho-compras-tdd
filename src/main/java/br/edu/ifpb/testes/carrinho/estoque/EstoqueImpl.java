package br.edu.ifpb.testes.carrinho.estoque;

import br.edu.ifpb.testes.carrinho.produto.Produto;
import br.edu.ifpb.testes.carrinho.produto.ProdutoDAO;
import br.edu.ifpb.testes.carrinho.produto.ProdutoDAOImpl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EstoqueImpl implements Estoque {

    private ProdutoDAO produtoDAO;

    public EstoqueImpl() {
        this.produtoDAO = new ProdutoDAOImpl();
    }

    public EstoqueImpl(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public boolean temItem(Produto produto) {
        try {
            return this.produtoDAO.listar().contains(produto);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Produto> getProdutosEstoque() {
        try {
            return this.produtoDAO.listar();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }

}
