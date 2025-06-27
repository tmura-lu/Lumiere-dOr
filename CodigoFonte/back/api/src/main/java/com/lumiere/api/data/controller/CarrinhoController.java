// src/main/java/com/lumiere/api/data/controller/CarrinhoController.java
package com.lumiere.api.data.controller;

import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal; // Importe
import java.util.Collections; // Importe, útil para respostas de erro

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
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
            @RequestBody ItemAdicionarRemoverRequest request) { // Usaremos uma classe Request para o body
        try {
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
            Carrinho carrinhoAtualizado = carrinhoService.removerItemDoCarrinho(
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

    // Endpoint para esvaziar o carrinho
    // DELETE /carrinhos/{usuarioId}/esvaziar
    @DeleteMapping("/{usuarioId}/esvaziar")
    public ResponseEntity<?> esvaziarCarrinho(@PathVariable Long usuarioId) {
        try {
            Carrinho carrinhoEsvaziado = carrinhoService.esvaziarCarrinho(usuarioId);
            return ResponseEntity.ok(carrinhoEsvaziado); // Retorna o carrinho agora vazio de itens
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
            return ResponseEntity.ok(Collections.singletonMap("total", total)); // Retorna um JSON com o total
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Classe interna para o corpo da requisição de adicionar/remover item
    static class ItemAdicionarRemoverRequest {
        private Long produtoId;
        private Integer quantidade;

        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
    }
}