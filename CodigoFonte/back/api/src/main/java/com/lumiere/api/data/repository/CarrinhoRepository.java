// src/main/java/com/lumiere/api/data/repository/CarrinhoRepository.java
package com.lumiere.api.data.repository;

import com.lumiere.api.data.entity.Carrinho;
import com.lumiere.api.data.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    // Método personalizado para buscar um carrinho pelo usuário
    Optional<Carrinho> findByUsuario(Usuario usuario);
}