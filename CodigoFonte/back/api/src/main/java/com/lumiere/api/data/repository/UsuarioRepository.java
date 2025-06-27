// src/main/java/com/lumiere.api.data.repository/UsuarioRepository.java
package com.lumiere.api.data.repository;
import com.lumiere.api.data.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}