package com.lumiere.api.data.service;

import com.lumiere.api.data.entity.Usuario;
import com.lumiere.api.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // <-- NOVO IMPORT

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired // <-- NOVO: Injeção do BCryptPasswordEncoder
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        // <-- NOVO: Criptografa a senha antes de salvar
        String senhaCriptografada = bCryptPasswordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> { // Nome da variável ajustado para clareza
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            // usuarioExistente.setTelefone(usuarioAtualizado.getTelefone()); // Telefone não está mais na classe Usuario
            // usuarioExistente.setTipo(usuarioAtualizado.getTipo()); // Verifique se 'tipo' deve ser atualizado ou se ele já é definido ao criar

            // <-- NOVO: Apenas criptografa e atualiza a senha se uma nova senha for fornecida
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                String novaSenhaCriptografada = bCryptPasswordEncoder.encode(usuarioAtualizado.getSenha());
                usuarioExistente.setSenha(novaSenhaCriptografada);
            }
            // Se 'tipo' é um campo que pode ser atualizado via API, adicione a linha abaixo:
            // usuarioExistente.setTipo(usuarioAtualizado.getTipo());

            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    // <-- NOVO: Método opcional para verificar a senha (útil para login)
    public boolean verificarSenha(String senhaCrua, String senhaCriptografada) {
        return bCryptPasswordEncoder.matches(senhaCrua, senhaCriptografada);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}