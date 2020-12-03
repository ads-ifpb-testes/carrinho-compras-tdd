package br.edu.ifpb.testes.carrinho.produto;

import java.util.Objects;

public class Produto implements Comparable<Produto> {

    private long id;
    private String nome;
    private long preco;

    public Produto(long id, String nome, long preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getPreco() {
        return preco;
    }

    public void setPreco(long preco) {
        this.preco = preco;
    }

    public int compareTo(Produto o) {
        if (this.preco > o.getPreco())
            return 1;
        if (this.preco < o.getPreco())
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
