// src/main/java/com/lumiere/api/data/service/CarrinhoService.java
package com.lumiere.api.data.service;

import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.entity.ItemCarrinho;
import com.lumiere.api.data.entity.Produto;
import com.lumiere.api.data.entity.Usuario;
import com.lumiere.api.data.repository.CarrinhoRepository;
import com.lumiere.api.data.repository.ItemCarrinhoRepository;
import com.lumiere.api.data.repository.ProdutoRepository;
import com.lumiere.api.data.repository.UsuarioRepository; // Para buscar o usuário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importe para transações

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
    private UsuarioRepository usuarioRepository; // Para buscar o usuário

    // Métodos CRUD básicos para Carrinho
    public Optional<Carrinho> buscarPorId(Long id) {
        return carrinhoRepository.findById(id);
    }

    public Carrinho salvar(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    @Transactional // Garante que a operação seja atômica
    public void deletar(Long id) {
        carrinhoRepository.deleteById(id);
    }

    /**
     * Busca ou cria um carrinho para um usuário.
     * @param usuarioId ID do usuário.
     * @return O carrinho do usuário.
     * @throws RuntimeException se o usuário não for encontrado.
     */
    @Transactional
    public Carrinho buscarOuCriarCarrinhoParaUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        return carrinhoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrinho novoCarrinho = new Carrinho(usuario);
                    return carrinhoRepository.save(novoCarrinho);
                });
    }

    /**
     * Adiciona um produto ao carrinho do usuário.
     * Se o item já existe, a quantidade é atualizada.
     *
     * @param usuarioId ID do usuário
     * @param produtoId ID do produto a ser adicionado
     * @param quantidade Quantidade a adicionar
     * @return O carrinho atualizado
     * @throws RuntimeException se usuário ou produto não forem encontrados, ou se a quantidade for inválida.
     */
    @Transactional
    public Carrinho adicionarItemAoCarrinho(Long usuarioId, Long produtoId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + produtoId));

        Optional<ItemCarrinho> itemExistente = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto);

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
            itemCarrinhoRepository.save(item); // Salva o item atualizado
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(carrinho, produto, quantidade);
            carrinho.adicionarItem(novoItem); // Usa o método da entidade para adicionar à lista e setar o carrinho
            // itemCarrinhoRepository.save(novoItem); // Não é necessário chamar save aqui devido ao cascade na relação
        }
        return carrinhoRepository.save(carrinho); // Salva o carrinho para persistir as mudanças nos itens
    }

    /**
     * Remove um produto do carrinho do usuário ou diminui sua quantidade.
     *
     * @param usuarioId ID do usuário
     * @param produtoId ID do produto a ser removido/atualizado
     * @param quantidadeRemover Quantidade a remover (se menor que a atual, atualiza; se igual ou maior, remove o item)
     * @return O carrinho atualizado
     * @throws RuntimeException se usuário ou produto não forem encontrados, ou se o item não estiver no carrinho.
     */
    @Transactional
    public Carrinho removerItemDoCarrinho(Long usuarioId, Long produtoId, Integer quantidadeRemover) {
        if (quantidadeRemover <= 0) {
            throw new IllegalArgumentException("A quantidade a remover deve ser maior que zero.");
        }

        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + produtoId));

        ItemCarrinho itemExistente = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho do usuário."));

        if (itemExistente.getQuantidade() <= quantidadeRemover) {
            // Remove o item completamente
            carrinho.removerItem(itemExistente); // Usa o método da entidade para remover da lista
            itemCarrinhoRepository.delete(itemExistente); // Deleta o item do banco de dados explicitamente
        } else {
            // Diminui a quantidade
            itemExistente.setQuantidade(itemExistente.getQuantidade() - quantidadeRemover);
            itemCarrinhoRepository.save(itemExistente); // Salva o item atualizado
        }
        return carrinhoRepository.save(carrinho); // Salva o carrinho para persistir as mudanças nos itens
    }

    /**
     * Esvazia completamente o carrinho de um usuário.
     * @param usuarioId ID do usuário.
     * @return O carrinho vazio.
     * @throws RuntimeException se o usuário não for encontrado.
     */
    @Transactional
    public Carrinho esvaziarCarrinho(Long usuarioId) {
        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);
        carrinho.getItens().clear(); // Limpa a lista de itens
        // Devido a orphanRemoval=true no @OneToMany, os itens serão deletados do DB
        return carrinhoRepository.save(carrinho); // Salva o carrinho para persistir as mudanças
    }

    /**
     * Calcula o valor total do carrinho.
     * @param carrinhoId ID do carrinho.
     * @return O valor total do carrinho.
     * @throws RuntimeException se o carrinho não for encontrado.
     */
    public BigDecimal calcularTotalCarrinho(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com o ID: " + carrinhoId));

        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrinho item : carrinho.getItens()) {
            BigDecimal subtotal = item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(subtotal);
        }
        return total;
    }
}