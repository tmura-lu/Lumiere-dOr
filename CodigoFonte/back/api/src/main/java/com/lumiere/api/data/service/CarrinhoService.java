// src/main/java/com/lumiere.api.data.service/CarrinhoService.java
package com.lumiere.api.data.service;

import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.entity.ItemCarrinho;
import com.lumiere.api.data.entity.Produto;
import com.lumiere.api.data.entity.Usuario; // Mudou de Cliente para Usuario
import com.lumiere.api.data.repository.CarrinhoRepository;
import com.lumiere.api.data.repository.ItemCarrinhoRepository;
import com.lumiere.api.data.repository.ProdutoRepository;
import com.lumiere.api.data.repository.UsuarioRepository; // Para buscar o usuário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    // Métodos adicionarItemAoCarrinho, removerItemDoCarrinho, esvaziarCarrinho, calcularTotalCarrinho
    // Permanece a mesma lógica, mas a variável 'usuario' será do tipo Usuario.
    // ... (mesmo código que o anterior, apenas com a variável 'usuario' sendo do tipo Usuario)
    @Transactional
    public Carrinho adicionarItemAoCarrinho(Long usuarioId, Long produtoId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        // Agora busca pelo Usuario diretamente, não Cliente
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId); // Reutiliza o método que já usa Usuario
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
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public Carrinho removerItemDoCarrinho(Long usuarioId, Long produtoId, Integer quantidadeRemover) {
        if (quantidadeRemover <= 0) {
            throw new IllegalArgumentException("A quantidade a remover deve ser maior que zero.");
        }

        // Agora busca pelo Usuario diretamente, não Cliente
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + produtoId));

        ItemCarrinho itemExistente = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho, produto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho do usuário."));

        if (itemExistente.getQuantidade() <= quantidadeRemover) {
            carrinho.removerItem(itemExistente);
            itemCarrinhoRepository.delete(itemExistente);
        } else {
            itemExistente.setQuantidade(itemExistente.getQuantidade() - quantidadeRemover);
            itemCarrinhoRepository.save(itemExistente);
        }
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public Carrinho esvaziarCarrinho(Long usuarioId) {
        Carrinho carrinho = buscarOuCriarCarrinhoParaUsuario(usuarioId);
        carrinho.getItens().clear();
        return carrinhoRepository.save(carrinho);
    }

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