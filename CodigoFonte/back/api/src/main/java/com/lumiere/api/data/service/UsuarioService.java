package com.lumiere.api.data.service;

import com.lumiere.api.data.entity.Usuario;
import com.lumiere.api.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        // Criptografa a senha antes de salvar
        String senhaCriptografada = bCryptPasswordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setTipo(usuarioAtualizado.getTipo()); // Atualiza o tipo

            // NOVOS CAMPOS: Atualiza CPF e Telefone
            usuarioExistente.setCpf(usuarioAtualizado.getCpf());
            usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());

            // Apenas criptografa e atualiza a senha se uma nova senha for fornecida
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                String novaSenhaCriptografada = bCryptPasswordEncoder.encode(usuarioAtualizado.getSenha());
                usuarioExistente.setSenha(novaSenhaCriptografada);
            }

            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    public boolean verificarSenha(String senhaCrua, String senhaCriptografada) {
        return bCryptPasswordEncoder.matches(senhaCrua, senhaCriptografada);
    }
}