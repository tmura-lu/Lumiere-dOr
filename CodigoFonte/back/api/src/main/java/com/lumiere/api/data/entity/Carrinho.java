// src/main/java/com/lumiere/api/data/entity/Carrinho.java
package com.lumiere.api.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinho")
public class Carrinho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento Um-Para-Um com Usuário
    // Cada usuário tem (no máximo) um carrinho. Um carrinho pertence a um único usuário.
    // Usamos JoinColumn para indicar a chave estrangeira na tabela 'carrinho'.
    // `unique = true` garante que apenas um carrinho por usuário pode existir.
    // `nullable = false` garante que um carrinho sempre esteja associado a um usuário.
    @OneToOne(fetch = jakarta.persistence.FetchType.LAZY) // Carregamento preguiçoso
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    // Relacionamento Um-Para-Muitos com ItemCarrinho
    // Um carrinho pode ter vários itens. Os itens "pertencem" ao carrinho.
    // `mappedBy = "carrinho"` indica que a propriedade 'carrinho' na entidade ItemCarrinho é quem gerencia o relacionamento.
    // `cascade = CascadeType.ALL` significa que operações como salvar, atualizar ou deletar um Carrinho
    // propagarão para os ItemCarrinho associados.
    // `orphanRemoval = true` garante que se um ItemCarrinho for removido da lista, ele seja deletado do banco de dados.
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>(); // Inicializa para evitar NullPointerExceptions

    @Column(nullable = false)
    private LocalDateTime dataCriacao; // Data de criação do carrinho

    @Column(nullable = false)
    private LocalDateTime ultimaAtualizacao; // Última data de atualização do carrinho

    // Construtor padrão (necessário para JPA)
    public Carrinho() {
        this.dataCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    // Construtor com usuário (para quando um novo carrinho é criado para um usuário)
    public Carrinho(Usuario usuario) {
        this(); // Chama o construtor padrão para inicializar datas
        this.usuario = usuario;
    }

    // --- Funções de Adicionar e Remover Itens (Lógica de Negócios no Carrinho) ---
    public void adicionarItem(ItemCarrinho item) {
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        item.setCarrinho(this); // Garante que o item esteja vinculado a este carrinho
        this.itens.add(item);
        this.ultimaAtualizacao = LocalDateTime.now(); // Atualiza a data
    }

    public void removerItem(ItemCarrinho item) {
        if (this.itens != null) {
            this.itens.remove(item);
            item.setCarrinho(null); // Desvincula o item do carrinho
            this.ultimaAtualizacao = LocalDateTime.now(); // Atualiza a data
        }
    }

    // Método para remover um item pelo produto ID (útil para o controller)
    public void removerItemPorProdutoId(Long produtoId) {
        if (this.itens != null) {
            this.itens.removeIf(item -> item.getProduto().getId().equals(produtoId));
            this.ultimaAtualizacao = LocalDateTime.now(); // Atualiza a data
        }
    }


    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    // Cuidado ao usar este setter diretamente, prefira adicionar/remover itens
    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", usuarioId=" + (usuario != null ? usuario.getId() : "null") +
                ", totalItens=" + (itens != null ? itens.size() : 0) +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                '}';
    }
}