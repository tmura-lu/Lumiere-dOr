// src/main/java/com/lumiere/api/data/entity/Produto.java
package com.lumiere.api.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal; // Importe para valores monetários

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2) // Ex: 99.999.999,99
    private BigDecimal preco;

    @Column(nullable = true) // Pode ter descrição opcional
    private String descricao;

    @Column(nullable = false)
    private Integer estoque; // Quantidade em estoque

    // Construtor padrão
    public Produto() {}

    // Construtor com parâmetros
    public Produto(String nome, BigDecimal preco, String descricao, Integer estoque) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.estoque = estoque;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                '}';
    }
}