// src/main/java/com/lumiere.api.data.service/CarrinhoService.java
package com.lumiere.api.data.service;

import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.entity.ItemCarrinho;
import com.lumiere.api.data.entity.Produto;
import com.lumiere.api.data.entity.Usuario;
import com.lumiere.api.data.repository.CarrinhoRepository;
import com.lumiere.api.data.repository.ItemCarrinhoRepository;
import com.lumiere.api.data.repository.ProdutoRepository;
import com.lumiere.api.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Helper method para inicializar os relacionamentos lazy de um Carrinho.
     * Deve ser chamado dentro de uma transação.
     */
    private void inicializarCarrinho(Carrinho carrinho) {
        if (carrinho == null) {
            return;
        }
        if (carrinho.getUsuario() != null) {
            Hibernate.initialize(carrinho.getUsuario());
        }
        if (carrinho.getItens() != null) {
            Hibernate.initialize(carrinho.getItens());
            carrinho.getItens().forEach(item -> {
                if (item.getProduto() != null) {
                    Hibernate.initialize(item.getProduto());
                }
            });
        }
    }

    @Transactional // <--- Adicionado @Transactional
    public Optional<Carrinho> buscarPorId(Long id) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
        carrinho.ifPresent(this::inicializarCarrinho); // Usa o helper method
        return carrinho;
    }

    // O método salvar não precisa de @Transactional se ele só chama um método do JpaRepository
    // Se fosse ter lógica de negócios complexa, aí sim.
    public Carrinho salvar(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public void deletar(Long id) {
        carrinhoRepository.deleteById(id);
    }

    @Transactional
    public Carrinho buscarOuCriarCarrinhoParaUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        Carrinho carrinho = carrinhoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrinho novoCarrinho = new Carrinho(usuario);
                    return carrinhoRepository.save(novoCarrinho);
                });

        inicializarCarrinho(carrinho); // Usa o helper method

        return carrinho;
    }

    @Transactional
    public Carrinho adicionarItemAoCarrinho(Long usuarioId, Long produtoId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // Busca o carrinho e já inicializa seus LAZYs (porque o método é @Transactional e chama inicializarCarrinho)
        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + produtoId));

        Optional<ItemCarrinho> itemExistente = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto);

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
            itemCarrinhoRepository.save(item);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(carrinho, produto, quantidade);
            carrinho.adicionarItem(novoItem);
        }

        // Salva o carrinho, garantindo que as mudanças cascatem para os itens
        Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);

        inicializarCarrinho(carrinhoAtualizado); // Usa o helper method no objeto atualizado

        return carrinhoAtualizado;
    }

    @Transactional
    public Carrinho removerItemDoCarrinho(Long usuarioId, Long produtoId, Integer quantidade) { // Parâmetro ajustado
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a remover deve ser maior que zero.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + produtoId));

        ItemCarrinho itemExistente = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho do usuário."));

        if (itemExistente.getQuantidade() <= quantidade) { // Usa 'quantidade'
            carrinho.removerItem(itemExistente);
            itemCarrinhoRepository.delete(itemExistente);
        } else {
            itemExistente.setQuantidade(itemExistente.getQuantidade() - quantidade); // Usa 'quantidade'
            itemCarrinhoRepository.save(itemExistente);
        }

        Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);

        inicializarCarrinho(carrinhoAtualizado); // Usa o helper method

        return carrinhoAtualizado;
    }

    @Transactional
    public Carrinho esvaziarCarrinho(Long usuarioId) {
        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);
        carrinho.getItens().clear();
        Carrinho carrinhoEsvaziado = carrinhoRepository.save(carrinho);

        inicializarCarrinho(carrinhoEsvaziado); // Usa o helper method

        return carrinhoEsvaziado;
    }

    @Transactional // <--- Adicionado @Transactional
    public BigDecimal calcularTotalCarrinho(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com o ID: " + carrinhoId));

        inicializarCarrinho(carrinho); // Usa o helper method

        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrinho item : carrinho.getItens()) {
            BigDecimal subtotal = item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(subtotal);
        }
        return total;
    }
}