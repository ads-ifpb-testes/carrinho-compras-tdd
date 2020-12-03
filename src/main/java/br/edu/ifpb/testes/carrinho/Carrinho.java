package br.edu.ifpb.testes.carrinho;

import br.edu.ifpb.testes.carrinho.produto.Produto;
import br.edu.ifpb.testes.carrinho.produto.ProdutoForaEstoqueException;

public interface Carrinho {

    void adicionarItem(Produto produto) throws ProdutoForaEstoqueException;

    int getQtdeItens();

    void removerItem(Produto produto);

    long getTotal();

}
