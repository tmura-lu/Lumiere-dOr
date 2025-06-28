describe('Adicionar produto ao carrinho', () => {
  const baseUrl = 'http://localhost:5173';

  const emailTeste = `teste`;
  const senhaTeste = 'teste';

  it('Deve adicionar um produto da categoria ÓCULOS ao carrinho', () => {
    // 1. Acessa a página de login
    cy.visit(baseUrl + '/login');

    // 2. Faz login pelo formulário
    cy.get('input[placeholder="Digite seu e-mail"]').type(emailTeste);
    cy.get('input[type="password"]').type(senhaTeste);
    cy.contains('Entrar').click();

    cy.wait(1000); // tempo para o login ser processado

    // Verifica se o login foi armazenado
    cy.window().then((win) => {
      const user = win.localStorage.getItem('usuario');
      expect(user).to.not.be.null;
    });

    // 4. Clica no menu "ÓCULOS" para abrir a categoria
    // Ajuste o seletor para o link ou botão do menu "ÓCULOS" do seu header
    cy.contains('Óculos').click();

    // 5. Espera os produtos da categoria carregarem
    cy.get('[data-cy="card-produto"]').should('have.length.greaterThan', 0);

    // 6. Pega o primeiro produto da lista
    cy.get('[data-cy="card-produto"]').first().as('primeiroProduto');

    // 7. Faz hover para aparecer o botão
    cy.get('@primeiroProduto').trigger('mouseover');

    cy.get('@primeiroProduto').find('[data-cy="comprar"]').click();

    // Antes de clicar no botão
    cy.window().then((win) => {
      cy.stub(win, 'alert').as('alertStub');
    });

    // 8. Clica no botão "Adicionar ao carrinho"
    cy.get('[data-cy="adicionar-ao-carrinho"]').click();

    // Aguarda o alerta aparecer e verifica se foi o certo
    cy.get('@alertStub').should('have.been.calledWith', 'Produto adicionado ao carrinho!');
  });
});
