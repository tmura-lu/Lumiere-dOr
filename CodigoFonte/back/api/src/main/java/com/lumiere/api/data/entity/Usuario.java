package com.lumiere.api.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "usuario") // Tabela "Usuario" no diagrama
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Int no diagrama

    @Column(nullable = false)
    private String nome; // VARCHAR no diagrama

    @Column(nullable = false, unique = true)
    private String email; // VARCHAR no diagrama

    @Column(nullable = false)
    private String senha; // VARCHAR no diagrama (armazenará criptografada)

    @Enumerated(EnumType.STRING) // Armazena o nome da enum (FUNCIONARIO ou CLIENTE) como String no DB
    @Column(nullable = false)
    private TipoUsuario tipo; // VARCHAR no diagrama - mantemos o enum por tipagem forte

    // NOVOS ATRIBUTOS: cpf e telefone
    @Column(nullable = false, unique = true) // CPF deve ser único e não nulo
    private String cpf;

    @Column(nullable = true) // Telefone pode ser nulo
    private String telefone;

    // Relacionamento Um-Para-Um com Carrinho
    // Cada usuário tem (no máximo) um carrinho. Um carrinho pertence a um único usuário.
    // `mappedBy = "usuario"` indica que a propriedade 'usuario' na entidade Carrinho é quem gerencia o relacionamento.
    // `cascade = CascadeType.ALL` e `orphanRemoval = true` para gerenciar o ciclo de vida do carrinho junto com o usuário.
    @JsonIgnore
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Carrinho carrinho;


    // Construtor padrão (necessário para JPA)
    public Usuario() {
    }

    // Construtor com campos (agora incluindo cpf e telefone)
    public Usuario(String nome, String email, String senha, TipoUsuario tipo, String cpf, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    // Getters e Setters para CPF e Telefone
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    // Ao setar o carrinho, também garante a consistência do lado do carrinho
    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
        if (carrinho != null) {
            carrinho.setUsuario(this);
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}