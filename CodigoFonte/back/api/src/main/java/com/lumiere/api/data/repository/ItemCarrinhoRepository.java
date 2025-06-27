// src/main/java/com.lumiere.api.data.repository/ItemCarrinhoRepository.java
package com.lumiere.api.data.repository;
import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.entity.ItemCarrinho;
import com.lumiere.api.data.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    Optional<ItemCarrinho> findByCarrinhoAndProduto(Carrinho carrinho, Produto produto);
}