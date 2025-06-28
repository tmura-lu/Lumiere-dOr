package com.lumiere.api.data.service.tests;

import com.lumiere.api.data.entity.TipoUsuario;
import com.lumiere.api.data.entity.Usuario;
import com.lumiere.api.data.repository.UsuarioRepository;
import com.lumiere.api.data.service.UsuarioService; // Importe o serviço real

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito; // Esta é a importação para a classe Mockito em si
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings; // Esta é a importação para a anotação @MockitoSettings
import org.mockito.quality.Strictness; // Importe para Strictness.LENIENT
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; // Importe estaticamente para asserções
import static org.mockito.ArgumentMatchers.any; // Importe estaticamente para any()
import static org.mockito.Mockito.when; // Importe estaticamente para when()

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Corrigido para Strictness.LENIENT
public class UsuarioServiceTest {

    @Mock // Cria um mock para o UsuarioRepository
    private UsuarioRepository usuarioRepository;

    @Mock // Cria um mock para o BCryptPasswordEncoder
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks // Injeta os mocks criados nas dependências de UsuarioService
    private UsuarioService usuarioService;

    // Método executado antes de cada teste.
    // Útil para configurar mocks ou inicializar dados comuns.
    @BeforeEach
    void setUp() {
        // Exemplo: mockar comportamento de save para não ir ao DB real
        // O Usuario aqui passado é genérico (any(Usuario.class)), então não precisa de CPF/Telefone específicos no mock setup
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuarioSalvo = invocation.getArgument(0);
            if (usuarioSalvo.getId() == null) {
                usuarioSalvo.setId(1L); // Simula o ID sendo gerado pelo DB
            }
            return usuarioSalvo;
        });
    }

    @Test // Marca o método como um teste
    @DisplayName("Deve criptografar a senha ao salvar um novo usuário") // Nome descritivo do teste
    void deveCriptografarSenhaAoSalvarUsuario() {
        // Cenario: Cria um Usuario com os novos atributos (nome, email, senha, tipo, cpf, telefone)
        Usuario novoUsuario = new Usuario("TestUser", "test@example.com", "senhaClara123", TipoUsuario.CLIENTE, "11122233344", "9988776655");
        String senhaCriptografadaSimulada = "hashedPasswordXYZ";

        // Mock comportamento do BCryptPasswordEncoder
        when(bCryptPasswordEncoder.encode("senhaClara123")).thenReturn(senhaCriptografadaSimulada);

        // Acao
        Usuario usuarioSalvo = usuarioService.salvar(novoUsuario);

        // Verificacao
        assertNotNull(usuarioSalvo.getId(), "O ID do usuário salvo não deveria ser nulo");
        assertEquals(senhaCriptografadaSimulada, usuarioSalvo.getSenha(), "A senha deveria ser criptografada");
        assertEquals("11122233344", usuarioSalvo.getCpf(), "O CPF deveria ser salvo corretamente");
        assertEquals("9988776655", usuarioSalvo.getTelefone(), "O telefone deveria ser salvo corretamente");
        // Opcional: verificar se o método encode foi realmente chamado
        // Mockito.verify(bCryptPasswordEncoder).encode("senhaClara123");
    }

    @Test
    @DisplayName("Deve retornar um usuário existente ao buscar por ID")
    void deveRetornarUsuarioExistenteAoBuscarPorId() {
        // Cenario: Cria um Usuario existente com os novos atributos
        Long usuarioId = 1L;
        Usuario usuarioExistente = new Usuario("ExistingUser", "exist@example.com", "senhaCriptografada", TipoUsuario.CLIENTE, "00011122233", "9911223344");
        usuarioExistente.setId(usuarioId);

        // Mock comportamento do findById
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));

        // Acao
        Optional<Usuario> resultado = usuarioService.buscarPorId(usuarioId);

        // Verificacao
        assertTrue(resultado.isPresent(), "Deveria encontrar o usuário");
        assertEquals(usuarioId, resultado.get().getId(), "O ID do usuário retornado deve corresponder");
        assertEquals("00011122233", resultado.get().getCpf(), "O CPF do usuário retornado deve corresponder");
        assertEquals("9911223344", resultado.get().getTelefone(), "O telefone do usuário retornado deve corresponder");
    }

    @Test
    @DisplayName("Não deve retornar usuário ao buscar por ID inexistente")
    void naoDeveRetornarUsuarioAoBuscarPorIdInexistente() {
        // Cenario
        Long usuarioIdInexistente = 999L;

        // Mock ESPECÍFICO PARA ESTE TESTE
        // Quando findById é chamado com o ID inexistente, ele deve retornar um Optional vazio
        when(usuarioRepository.findById(usuarioIdInexistente)).thenReturn(Optional.empty());

        // Acao
        Optional<Usuario> resultado = usuarioService.buscarPorId(usuarioIdInexistente);

        // Verificacao
        assertFalse(resultado.isPresent(), "Não deveria encontrar o usuário para um ID inexistente");
    }

    // Você pode adicionar mais testes aqui, por exemplo:
    // - Teste para atualizar usuário (senha criptografada, outros campos incluindo CPF e Telefone)
    // - Teste para deletar usuário
    // - Testes de validação de CPF/Telefone (se tiver lógica de validação no service)
}
