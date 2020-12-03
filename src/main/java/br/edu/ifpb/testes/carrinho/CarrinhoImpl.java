package br.edu.ifpb.testes.carrinho;

import br.edu.ifpb.testes.carrinho.estoque.Estoque;
import br.edu.ifpb.testes.carrinho.estoque.EstoqueImpl;
import br.edu.ifpb.testes.carrinho.produto.Produto;
import br.edu.ifpb.testes.carrinho.produto.ProdutoForaEstoqueException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CarrinhoImpl implements Carrinho {

    private List<Produto> produtos = new LinkedList<Produto>();
    private Estoque estoque;

    public CarrinhoImpl() {
        this.estoque = new EstoqueImpl();
    }

    public CarrinhoImpl(Estoque estoque) {
        this.estoque = estoque;
    }

    public void adicionarItem(Produto produto) throws ProdutoForaEstoqueException {
        if (!estoque.temItem(produto))
            throw new ProdutoForaEstoqueException("Produto fora de estoque");
        this.produtos.add(produto);
        if (produtos.size() >= 4) {
            Collections.sort(this.produtos);
        }
    }

    public int getQtdeItens() {
        return this.produtos.size();
    }

    public void removerItem(Produto produto) {
        this.produtos.remove(produto);
    }

    public long getTotal() {
        Long total = 0L;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        if (this.produtos.size() >= 4)
            total -= this.produtos.get(0).getPreco();
        return total;
    }
}
