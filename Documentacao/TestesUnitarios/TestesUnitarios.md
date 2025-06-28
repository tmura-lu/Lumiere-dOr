# Documentação de Testes Unitários - Projeto Lumiere API

## 1. Visão Geral da Estratégia de Testes Unitários

Este documento detalha a implementação e execução dos testes unitários para a API do projeto Lumiere, com o objetivo de garantir a correção e robustez da lógica interna dos componentes.

* **Ferramentas Utilizadas:**
    * **JUnit 5:** Framework para escrita e execução de testes.
    * **Mockito:** Biblioteca para criação de mocks e stubs, permitindo o isolamento das unidades de teste.
    * **JaCoCo:** Plugin Maven para análise de cobertura de código, gerando relatórios detalhados.
* **Localização dos Testes:**
    Os arquivos de teste estão localizados no diretório `src/test/java/com/lumiere/api/data/service/`.
* **Como Executar os Testes:**
    * Via Maven (terminal): `mvn clean verify` (executa testes e gera relatório de cobertura).
    * Via IDE (IntelliJ IDEA): Clique com o botão direito na classe de teste (ex: `UsuarioServiceTest.java`) e selecione "Run 'UsuarioServiceTest'".
* **Como Acessar o Relatório de Cobertura (JaCoCo):**
    Após executar `mvn clean verify`, o relatório HTML será gerado em: `target/site/jacoco/index.html`. Abra este arquivo em um navegador web.

---

## 2. Detalhamento dos Casos de Teste Unitários

### **2.1. Caso de Teste: UT-USU-001**

* **Funcionalidade/Método Testado:** `UsuarioService.salvar(Usuario usuario)`
* **Cenário de Teste:** Deve criptografar a senha ao salvar um novo usuário.
* **Pré-condições/Configuração (`Arrange`):**
    * Um objeto `Usuario` é criado com uma senha em texto claro.
    * O mock `bCryptPasswordEncoder.encode()` é configurado para retornar uma senha criptografada simulada quando chamado com a senha em texto claro.
    * O mock `usuarioRepository.save()` é configurado para retornar o `Usuario` com um ID simulado.
* **Ação (`Act`):**
    * `usuarioService.salvar(novoUsuario);`
* **Resultado Esperado (`Assert`):**
    * O ID do `usuarioSalvo` não deve ser nulo.
    * A senha do `usuarioSalvo` deve ser igual à senha criptografada simulada retornada pelo mock.
* **Print Screen do Resultado da Execução do Teste:**
    (Inserir imagem do teste passando no IntelliJ/terminal)
* **Status:** [ ] Aprovado / [ ] Reprovado

### **2.2. Caso de Teste: UT-USU-002**

* **Funcionalidade/Método Testado:** `UsuarioService.buscarPorId(Long id)`
* **Cenário de Teste:** Deve retornar um usuário existente ao buscar por ID.
* **Pré-condições/Configuração (`Arrange`):**
    * Um `usuarioId` e um objeto `Usuario` existente são definidos.
    * O mock `usuarioRepository.findById()` é configurado para retornar o `Optional.of(usuarioExistente)` quando chamado com o `usuarioId`.
* **Ação (`Act`):**
    * `usuarioService.buscarPorId(usuarioId);`
* **Resultado Esperado (`Assert`):**
    * O resultado deve ser um `Optional` presente.
    * O ID do usuário retornado deve corresponder ao `usuarioId`.
* **Print Screen do Resultado da Execução do Teste:**
    (Inserir imagem do teste passando no IntelliJ/terminal)
* **Status:** [ ] Aprovado / [ ] Reprovado

### **2.3. Caso de Teste: UT-USU-003**

* **Funcionalidade/Método Testado:** `UsuarioService.buscarPorId(Long id)`
* **Cenário de Teste:** Não deve retornar usuário ao buscar por ID inexistente.
* **Pré-condições/Configuração (`Arrange`):**
    * Um `usuarioIdInexistente` é definido (ex: 999L).
    * O mock `usuarioRepository.findById()` é configurado para retornar um `Optional.empty()` quando chamado com o `usuarioIdInexistente`.
* **Ação (`Act`):**
    * `usuarioService.buscarPorId(usuarioIdInexistente);`
* **Resultado Esperado (`Assert`):**
    * O resultado deve ser um `Optional` vazio (`isPresent()` deve ser `false`).
* **Print Screen do Resultado da Execução do Teste:**
    (Inserir imagem do teste passando no IntelliJ/terminal)
* **Status:** [ ] Aprovado / [ ] Reprovado

### **2.4. Caso de Teste: UT-CARR-001**

* **Funcionalidade/Método Testado:** `CarrinhoService.adicionarItemAoCarrinho(...)`
* **Cenário de Teste:** Deve adicionar um novo item ao carrinho se o produto não existir previamente.
* **Pré-condições/Configuração (`Arrange`):**
    * Objetos mock `Usuario`, `Produto`, `Carrinho` são criados.
    * Mocks para `usuarioRepository`, `produtoRepository`, `carrinhoRepository` e `itemCarrinhoRepository` são configurados para simular a busca de entidades e o retorno de um `Optional.empty()` para `findByCarrinhoAndProduto`.
    * O comportamento `Hibernate.initialize()` é mockado.
* **Ação (`Act`):**
    * `carrinhoService.adicionarItemAoCarrinho(usuario.getId(), produto1.getId(), 1);`
* **Resultado Esperado (`Assert`):**
    * O `Carrinho` retornado não deve ser nulo.
    * A lista de `itens` do `Carrinho` deve ter tamanho 1.
    * O produto no item adicionado deve corresponder ao `produto1`.
    * A quantidade do item deve ser 1.
    * `itemCarrinhoRepository.save()` não deve ser chamado (devido ao cascade).
    * `carrinhoRepository.save()` deve ser chamado uma vez.
* **Print Screen do Resultado da Execução do Teste:**
    (Inserir imagem do teste passando no IntelliJ/terminal)
* **Status:** [ ] Aprovado / [ ] Reprovado

### **2.5. Caso de Teste: UT-CARR-002**

* **Funcionalidade/Método Testado:** `CarrinhoService.adicionarItemAoCarrinho(...)`
* **Cenário de Teste:** Deve aumentar a quantidade de um item existente no carrinho.
* **Pré-condições/Configuração (`Arrange`):**
    * Objetos mock `Usuario`, `Produto`, `Carrinho` são criados.
    * Um `ItemCarrinho` existente com uma quantidade inicial é adicionado ao `Carrinho`.
    * Mocks para repositórios são configurados, e `itemCarrinhoRepository.findByCarrinhoAndProduto()` é configurado para retornar o `ItemCarrinho` existente.
* **Ação (`Act`):**
    * `carrinhoService.adicionarItemAoCarrinho(usuario.getId(), produto1.getId(), 3);` (onde 3 é a quantidade a ser adicionada)
* **Resultado Esperado (`Assert`):**
    * O `Carrinho` retornado não deve ser nulo.
    * A lista de `itens` do `Carrinho` deve ter tamanho 1 (ainda).
    * O produto no item deve corresponder ao `produto1`.
    * A quantidade do item deve ser a soma da quantidade inicial e a quantidade adicionada (ex: 2 + 3 = 5).
    * `itemCarrinhoRepository.save()` deve ser chamado uma vez (para atualizar o item).
    * `carrinhoRepository.save()` deve ser chamado uma vez.
* **Print Screen do Resultado da Execução do Teste:**
    (Inserir imagem do teste passando no IntelliJ/terminal)
* **Status:** [ ] Aprovado / [ ] Reprovado

---

## 3. Análise da Cobertura de Código (JaCoCo)

Após executar `mvn clean verify`, acesse o relatório JaCoCo em `target/site/jacoco/index.html`.

* **Percentual de Cobertura Total:** [Preencher com o percentual real do seu relatório]
    * Ex: Linhas: 85%, Branches: 70%, Métodos: 90%, Classes: 100%
* **Classes/Métodos com Alta Cobertura:**
    * `UsuarioService`: Métodos `salvar`, `buscarPorId` (se testados).
    * `CarrinhoService`: Métodos `adicionarItemAoCarrinho`, `calcularTotalCarrinho` (se testados).
* **Classes/Métodos com Baixa Cobertura (se houver):**
    * [Mencionar classes/métodos específicos e, se souber, o motivo. Ex: `UsuarioService.deletar()` se não foi testado.]
* **Interpretação:** A cobertura X% indica que Y% das linhas do código foram executadas pelos testes. Isso ajuda a identificar quais partes do sistema ainda não possuem testes unitários e podem ser pontos de falha potenciais. Recomenda-se aumentar a cobertura das partes críticas do sistema.