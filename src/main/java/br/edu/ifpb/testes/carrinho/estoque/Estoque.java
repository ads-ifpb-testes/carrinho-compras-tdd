package br.edu.ifpb.testes.carrinho.estoque;

import br.edu.ifpb.testes.carrinho.produto.Produto;

import java.util.List;

public interface Estoque {

    boolean temItem(Produto produto);
    List<Produto> getProdutosEstoque();

}
