// src/main/java/com/lumiere.api.data.controller/ProdutoController.java
package com.lumiere.api.data.controller;

import com.lumiere.api.data.entity.Produto;
import com.lumiere.api.data.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos") // URL base para todos os endpoints de produto
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Endpoint para buscar todos os produtos.
     * URL: GET http://localhost:8080/produtos
     * @return Uma lista de todos os produtos.
     */
    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    /**
     * Endpoint para buscar um produto por ID.
     * URL: GET http://localhost:8080/produtos/{id} (ex: http://localhost:8080/produtos/1)
     * @param id O ID do produto a ser buscado.
     * @return ResponseEntity com o produto encontrado ou status 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar um novo produto.
     * URL: POST http://localhost:8080/produtos
     * Corpo da Requisição (JSON):
     * {
     * "nome": "Nome do Produto",
     * "tipo": "Categoria",
     * "descricao": "Descrição detalhada do produto.",
     * "preco": 99.99,
     * "estoque": 100,
     * "urlImagem": "http://example.com/imagem.jpg"
     * }
     * @param produto O objeto Produto a ser criado.
     * @return ResponseEntity com o produto criado e status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto savedProduto = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    /**
     * Endpoint para atualizar um produto existente.
     * URL: PUT http://localhost:8080/produtos/{id} (ex: http://localhost:8080/produtos/1)
     * Corpo da Requisição (JSON) - deve conter todos os campos que deseja atualizar:
     * {
     * "id": 1, // Opcional, mas útil para consistência
     * "nome": "Nome Atualizado",
     * "tipo": "Nova Categoria",
     * "descricao": "Nova descrição.",
     * "preco": 109.99,
     * "estoque": 90,
     * "urlImagem": "http://example.com/nova_imagem.jpg"
     * }
     * @param id O ID do produto a ser atualizado.
     * @param produto Os dados atualizados do produto.
     * @return ResponseEntity com o produto atualizado ou status 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produto.setId(id); // Garante que o ID do objeto corresponde ao ID da URL
        Produto updatedProduto = produtoRepository.save(produto);
        return ResponseEntity.ok(updatedProduto);
    }

    /**
     * Endpoint para deletar um produto por ID.
     * URL: DELETE http://localhost:8080/produtos/{id} (ex: http://localhost:8080/produtos/1)
     * @param id O ID do produto a ser deletado.
     * @return ResponseEntity com status 204 No Content se a deleção for bem-sucedida, ou 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content para deleção bem-sucedida
    }
}