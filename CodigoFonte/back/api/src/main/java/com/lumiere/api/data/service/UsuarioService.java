package com.lumiere.api.data.service;

import com.lumiere.api.data.entity.Usuario; // Mudança aqui: de Cliente para Usuario
import com.lumiere.api.data.repository.UsuarioRepository; // Mudança aqui: de Cliente para Usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService { // Mudança aqui: de ClienteService para UsuarioService

    @Autowired
    private UsuarioRepository usuarioRepository; // Mudança aqui: de ClienteRepository para UsuarioRepository

    public List<Usuario> buscarTodos() { // Mudança aqui: de List<Cliente> para List<Usuario>
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) { // Mudança aqui: de Optional<Cliente> para Optional<Usuario>
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) { // Mudança aqui: de Cliente salvar(Cliente cliente) para Usuario salvar(Usuario usuario)
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) { // Mudança aqui: de Cliente clienteAtualizado para Usuario usuarioAtualizado
        return usuarioRepository.findById(id).map(usuario -> { // Mudança aqui: de cliente -> para usuario ->
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id)); // Mudança aqui: Cliente não encontrado para Usuário não encontrado
    }
}