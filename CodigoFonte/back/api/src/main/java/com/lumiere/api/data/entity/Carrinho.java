// src/main/java/com/lumiere.api.data.entity/Carrinho.java
package com.lumiere.api.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column; // Para dataCriacao e ultimaAtualizacao, se mantiver
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime; // Se mantiver as datas
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinho") // Tabela "Carrinho" no diagrama
public class Carrinho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Int no diagrama

    // Relacionamento Um-Para-Um com Usuario
    // id_cliente no diagrama agora aponta para o Usuario geral.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true, nullable = false) // Nome da coluna FK no DB
    private Usuario usuario; // Relaciona diretamente com Usuario

    // Relacionamento Um-Para-Muitos com Itens_Carrinho
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemCarrinho> itens = new ArrayList<>();

    // Mantendo atributos de controle de tempo por boa prática, embora não no diagrama para Carrinho
    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime ultimaAtualizacao;

    // Construtor padrão
    public Carrinho() {
        this.dataCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    // Construtor com usuário
    public Carrinho(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    // --- Funções de Adicionar e Remover Itens (mantidas) ---
    public void adicionarItem(ItemCarrinho item) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        item.setCarrinho(this);
        this.itens.add(item);
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void removerItem(ItemCarrinho item) {
        if (this.itens != null) {
            this.itens.remove(item);
            item.setCarrinho(null);
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }

    public void removerItemPorProdutoId(Long produtoId) {
        if (this.itens != null) {
            this.itens.removeIf(item -> item.getProduto().getId().equals(produtoId));
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<ItemCarrinho> getItens() { return itens; }
    public void setItens(List<ItemCarrinho> itens) { this.itens = itens; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }

    // Exemplo de toString() seguro para Carrinho
    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", usuarioId=" + (usuario != null ? usuario.getId() : "null") + // Acessa apenas o ID do usuário
                ", totalItens=" + (itens != null ? itens.size() : 0) + // Acessa o tamanho da lista de itens
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                '}';
    }
}