// src/main/java/com/lumiere.api.data.controller/CarrinhoController.java
package com.lumiere.api.data.controller;

import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.service.CarrinhoService; // Verifica se este import está correto para o seu pacote!
import org.springframework.beans.factory.annotation.Autowired; // Verifique o import de Autowired
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired // Corrigido de @Autowireda para @Autowired
    private CarrinhoService carrinhoService;

    // Endpoint para obter o carrinho de um usuário
    // GET /carrinhos/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getCarrinhoByUsuarioId(@PathVariable Long usuarioId) {
        try {
            Carrinho carrinho = carrinhoService.buscarOuCriarCarrinhoParaUsuario(usuarioId);
            return ResponseEntity.ok(carrinho);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Endpoint para adicionar item ao carrinho
    // POST /carrinhos/{usuarioId}/adicionar
    // Body: { "produtoId": 1, "quantidade": 2 }
    @PostMapping("/{usuarioId}/adicionar")
    public ResponseEntity<?> adicionarItem(
            @PathVariable Long usuarioId,
            @RequestBody ItemAdicionarRemoverRequest request) {
        try {
            // Chama o método no service. O service espera 'quantidade'
            Carrinho carrinhoAtualizado = carrinhoService.adicionarItemAoCarrinho(
                    usuarioId, request.getProdutoId(), request.getQuantidade());
            return ResponseEntity.ok(carrinhoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Endpoint para remover item do carrinho
    // POST /carrinhos/{usuarioId}/remover
    // Body: { "produtoId": 1, "quantidade": 1 }
    @PostMapping("/{usuarioId}/remover")
    public ResponseEntity<?> removerItem(
            @PathVariable Long usuarioId,
            @RequestBody ItemAdicionarRemoverRequest request) {
        try {
            // O service espera 'quantidadeRemover' no método 'removerItemDoCarrinho'.
            // A classe ItemAdicionarRemoverRequest precisa ter um getter para 'quantidadeRemover'.
            Carrinho carrinhoAtualizado = carrinhoService.removerItemDoCarrinho(
                    usuarioId, request.getProdutoId(), request.getQuantidade()); // <--- Corrigido para request.getQuantidade()
            return ResponseEntity.ok(carrinhoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Endpoint para esvaziar o carrinho
    // DELETE /carrinhos/{usuarioId}/esvaziar
    @DeleteMapping("/{usuarioId}/esvaziar")
    public ResponseEntity<?> esvaziarCarrinho(@PathVariable Long usuarioId) {
        try {
            Carrinho carrinhoEsvaziado = carrinhoService.esvaziarCarrinho(usuarioId);
            return ResponseEntity.ok(carrinhoEsvaziado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Endpoint para calcular o total do carrinho
    // GET /carrinhos/{carrinhoId}/total
    @GetMapping("/{carrinhoId}/total")
    public ResponseEntity<?> getValorTotalCarrinho(@PathVariable Long carrinhoId) {
        try {
            BigDecimal total = carrinhoService.calcularTotalCarrinho(carrinhoId);
            return ResponseEntity.ok(Collections.singletonMap("total", total));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Classe interna para o corpo da requisição de adicionar/remover item
    // Esta classe deve ser capaz de mapear tanto "quantidade" quanto "quantidadeRemover"
    // Dependendo de qual campo você usa no seu JSON de entrada.
    static class ItemAdicionarRemoverRequest {
        private Long produtoId;
        private Integer quantidade; // Usado para 'adicionar' e 'remover'

        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public Integer getQuantidade() { // Getter para o campo 'quantidade'
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) { // Setter para o campo 'quantidade'
            this.quantidade = quantidade;
        }

        // Se você decidiu usar 'quantidadeRemover' no JSON e no Service, adicione isto:
        /*
        private Integer quantidadeRemover;
        public Integer getQuantidadeRemover() {
            return quantidadeRemover;
        }
        public void setQuantidadeRemover(Integer quantidadeRemover) {
            this.quantidadeRemover = quantidadeRemover;
        }
        */
    }
}