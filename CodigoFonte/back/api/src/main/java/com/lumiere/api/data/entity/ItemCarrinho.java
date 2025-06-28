// src/main/java/com/lumiere.api.data.entity/ItemCarrinho.java
package com.lumiere.api.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "itens_carrinho", // Tabela "Itens_Carrinho" no diagrama
        uniqueConstraints = @UniqueConstraint(columnNames = {"carrinho_id", "produto_id"})) // Garante item único por carrinho
public class ItemCarrinho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Int no diagrama

    // Relacionamento Muitos-Para-Um com Carrinho
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id", nullable = false) // id_carrinho no diagrama
    private Carrinho carrinho;

    // Relacionamento Muitos-Para-Um com Produto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false) // id_produto no diagrama
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade; // Int no diagrama

    // Construtor padrão
    public ItemCarrinho() {}

    // Construtor com parâmetros
    public ItemCarrinho(Carrinho carrinho, Produto produto, Integer quantidade) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Carrinho getCarrinho() { return carrinho; }
    public void setCarrinho(Carrinho carrinho) { this.carrinho = carrinho; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

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