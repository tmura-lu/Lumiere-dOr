// src/main/java/com/lumiere.api.data.entity/Produto.java
package com.lumiere.api.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "produto") // Tabela "Produto" no diagrama
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Int no diagrama

    @Column(nullable = false)
    private String nome; // VARCHAR no diagrama

    @Column(nullable = true) // 'tipo' no diagrama, VARCHAR
    private String tipo; // Tipo do produto (ex: "eletronico", "roupa", "livro")

    @Column(nullable = true)
    private String descricao; // VARCHAR no diagrama

    @Column(nullable = false, precision = 10, scale = 2) // Decimal(10,2) no diagrama
    private BigDecimal preco;

    @Column(name = "qtd_estoque", nullable = false) // Mapeia para qtd_estoque
    private Integer estoque; // Int no diagrama

    @Column(name = "url_imagem", nullable = true) // VARCHAR no diagrama
    private String urlImagem;

    // Construtor padrão
    public Produto() {}

    // Construtor com parâmetros
    public Produto(String nome, String tipo, String descricao, BigDecimal preco, Integer estoque, String urlImagem) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.urlImagem = urlImagem;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }

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