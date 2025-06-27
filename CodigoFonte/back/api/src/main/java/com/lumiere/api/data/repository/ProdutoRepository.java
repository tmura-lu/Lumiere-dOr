// src/main/java/com/lumiere/api/data/repository/ProdutoRepository.java
package com.lumiere.api.data.repository;

import com.lumiere.api.data.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}