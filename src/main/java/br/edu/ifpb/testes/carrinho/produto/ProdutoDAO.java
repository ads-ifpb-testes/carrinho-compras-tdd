package br.edu.ifpb.testes.carrinho.produto;

import java.sql.SQLException;
import java.util.List;

public interface ProdutoDAO {
    
    void salvar(Produto p) throws SQLException;
    List<Produto> listar() throws SQLException;
    
}
