// src/main/java/com/lumiere/api/data/entity/ItemCarrinho.java
package com.lumiere.api.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint; // Importe para restrição única
import java.io.Serializable;

@Entity
@Table(name = "item_carrinho",
        uniqueConstraints = @UniqueConstraint(columnNames = {"carrinho_id", "produto_id"})) // Garante que um produto só aparece uma vez por carrinho
public class ItemCarrinho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento Muitos-Para-Um com Carrinho
    // Muitos ItemCarrinho podem estar em um Carrinho.
    // `JoinColumn(name = "carrinho_id")` define a coluna da chave estrangeira.
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;

    // Relacionamento Muitos-Para-Um com Produto
    // Muitos ItemCarrinho podem se referir a um Produto.
    // `JoinColumn(name = "produto_id")` define a coluna da chave estrangeira.
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade; // Quantidade do produto neste item do carrinho

    // Construtor padrão
    public ItemCarrinho() {}

    // Construtor com parâmetros
    public ItemCarrinho(Carrinho carrinho, Produto produto, Integer quantidade) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemCarrinho{" +
                "id=" + id +
                ", carrinhoId=" + (carrinho != null ? carrinho.getId() : "null") +
                ", produtoId=" + (produto != null ? produto.getId() : "null") +
                ", quantidade=" + quantidade +
                '}';
    }
}