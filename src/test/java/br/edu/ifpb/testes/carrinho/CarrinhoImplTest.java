package br.edu.ifpb.testes.carrinho;

import br.edu.ifpb.testes.carrinho.estoque.Estoque;
import br.edu.ifpb.testes.carrinho.produto.Produto;
import br.edu.ifpb.testes.carrinho.produto.ProdutoForaEstoqueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class CarrinhoImplTest {

    private CarrinhoImpl carrinhoImpl;

    @Mock
    private Estoque estoque;

    @Before
    public void prepararCenario() {
        // Preparar o cenário
        MockitoAnnotations.initMocks(this);
        Mockito.when(estoque.temItem(Mockito.any(Produto.class))).thenReturn(true);
        this.carrinhoImpl = new CarrinhoImpl(estoque);
    }

    @Test
    public void inserirProduto() throws ProdutoForaEstoqueException {
        // Realizar o estímulo ao sistema
        Produto produto = new Produto(1, "Mouse", 17L);
        carrinhoImpl.adicionarItem(produto);

        // Comparar a saída com o que eu espero (requisito)
        Assert.assertEquals(1, carrinhoImpl.getQtdeItens());
    }

    @Test
    public void removerProduto() throws ProdutoForaEstoqueException {
        // Realizar o estímulo ao sistema
        Produto produto = new Produto(1, "Mouse", 17L);
        carrinhoImpl.adicionarItem(produto);
        carrinhoImpl.removerItem(produto);

        Assert.assertEquals(0, carrinhoImpl.getQtdeItens());
    }

    @Test
    public void testarTotal() throws ProdutoForaEstoqueException {
        // Realizar o estímulo ao sistema
        Produto produto1 = new Produto(1, "Mouse", 17L);
        Produto produto2 = new Produto(2, "Teclado", 50L);
        Produto produto3 = new Produto(3, "Monitor", 1000L);
        carrinhoImpl.adicionarItem(produto1);
        carrinhoImpl.adicionarItem(produto2);
        carrinhoImpl.adicionarItem(produto3);

        Assert.assertEquals(1067L, carrinhoImpl.getTotal());
    }

    @Test
    public void testarTotalComDescontoComprasAcima4() throws ProdutoForaEstoqueException {

        // Realizar o estímulo ao sistema
        Produto produto1 = new Produto(1, "Mouse", 50L);
        Produto produto2 = new Produto(2, "Teclado", 50L);
        Produto produto3 = new Produto(3, "Monitor", 1000L);
        Produto produto4 = new Produto(4, "Mousepad", 50L);

        carrinhoImpl.adicionarItem(produto1);
        carrinhoImpl.adicionarItem(produto2);
        carrinhoImpl.adicionarItem(produto3);
        carrinhoImpl.adicionarItem(produto4);

        Assert.assertEquals(1100, carrinhoImpl.getTotal());
    }

    @Test
    public void adicionarItemComEstoque() throws ProdutoForaEstoqueException {
        // Realizar o estímulo ao sistema
        Produto produto1 = new Produto(1, "Mouse", 50L);
        carrinhoImpl.adicionarItem(produto1);
        Assert.assertEquals(1, carrinhoImpl.getQtdeItens());
    }

    @Test(expected = ProdutoForaEstoqueException.class)
    public void adicionarItemSemEstoque() throws ProdutoForaEstoqueException {
        Mockito.when(estoque.temItem(Mockito.any(Produto.class))).thenReturn(false);

        List<Produto> produtosMock = new LinkedList<>();
        produtosMock.add(new Produto(7, "Mesa digitalizadora", 365L));
        Mockito.when(estoque.getProdutosEstoque()).thenReturn(produtosMock);

        // Realizar o estímulo ao sistema
        Produto produto1 = new Produto(1, "Mouse", 50L);
        carrinhoImpl.adicionarItem(produto1);
    }

}
