package br.edu.ifpb.testes.carrinho;

import br.edu.ifpb.testes.carrinho.estoque.Estoque;
import br.edu.ifpb.testes.carrinho.estoque.EstoqueImpl;
import br.edu.ifpb.testes.carrinho.produto.Produto;
import br.edu.ifpb.testes.carrinho.produto.ProdutoDAO;
import br.edu.ifpb.testes.carrinho.produto.ProdutoDAOImpl;
import br.edu.ifpb.testes.carrinho.produto.ProdutoForaEstoqueException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;

public class CarrinhoImplIntegrationTest {

    private JdbcDatabaseContainer container;
    private IDatabaseTester databaseTester;
    private Carrinho carrinho;
    private Connection conexao;

    @Before
    public void setUp() throws Exception {
        if(container == null) {
            PostgreSQLContainer postgreSQLContainer;
            postgreSQLContainer = new PostgreSQLContainer().withUsername("postgresql").withPassword("123456").withDatabaseName("exemplo-testes");
            container = postgreSQLContainer.withInitScript("postgres/create_schema.sql");
            container.start();
            conexao = DriverManager.getConnection(container.getJdbcUrl(), "postgresql", "123456");
        }
        configurarDBUnit();
        this.databaseTester.onSetup();
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(conexao);
        Estoque estoque = new EstoqueImpl(produtoDAO);
        carrinho = new CarrinhoImpl(estoque);
    }

    private void configurarDBUnit() throws ClassNotFoundException, FileNotFoundException, DataSetException {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(("src/test/resources/produtos_testset.xml")));
        this.databaseTester = new JdbcDatabaseTester("org.postgresql.Driver",
                container.getJdbcUrl(), "postgresql", "123456");
        this.databaseTester.setDataSet(dataSet);
        this.databaseTester.setSetUpOperation(DatabaseOperation.INSERT);
        this.databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
    }

    @Test
    public void consultarEstoqueDisponivel() throws ProdutoForaEstoqueException {
        Produto produtoDisponivel = new Produto(1,
                "Livro - Como Quebrar Software: 1001 receitas pra deixar DEV puto",
                (long) 55.0);
        this.carrinho.adicionarItem(produtoDisponivel);
        Assert.assertEquals(1, this.carrinho.getQtdeItens());
    }

    @Test(expected = ProdutoForaEstoqueException.class)
    public void consultarQuandoEstoqueIndisponivel() throws ProdutoForaEstoqueException {
        Produto produtoIndisponivel = new Produto(15,
                "Livro - Introdução e Boas Práticas em UX Design",
                (long) 75.0);
        this.carrinho.adicionarItem(produtoIndisponivel);
    }

    @After
    public void finalizar() throws Exception {
        databaseTester.onTearDown();
        conexao.close();
    }


}
