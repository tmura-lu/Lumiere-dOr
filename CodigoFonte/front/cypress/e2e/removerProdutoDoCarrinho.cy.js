describe('Adicionar produto ao carrinho e remover do carrinho', () => {
  const baseUrl = 'http://localhost:5173';

  const emailTeste = `teste`;
  const senhaTeste = 'teste';

  it('Deve adicionar um produto da categoria ÓCULOS ao carrinho, remover e confirmar', () => {
    // 1. Login (como já tem)
    cy.visit(baseUrl + '/login');
    cy.get('input[placeholder="Digite seu e-mail"]').type(emailTeste);
    cy.get('input[type="password"]').type(senhaTeste);
    cy.contains('Entrar').click();

    cy.wait(1000); // espera o login

    cy.window().then((win) => {
      cy.stub(win, 'alert').as('alertStub'); // stuba alert antes de ações que disparam alert
      const user = win.localStorage.getItem('usuario');
      expect(user).to.not.be.null;
    });

    // 2. Navega para categoria Óculos
    cy.contains('Óculos').click();

    // 3. Espera os produtos carregarem e seleciona o primeiro
    cy.get('[data-cy="card-produto"]').should('have.length.greaterThan', 0);
    cy.get('[data-cy="card-produto"]').first().as('primeiroProduto');
    cy.get('@primeiroProduto').trigger('mouseover');
    cy.get('@primeiroProduto').find('[data-cy="comprar"]').click();

    // 4. Clica no botão adicionar ao carrinho
    cy.get('[data-cy="adicionar-ao-carrinho"]').click();

    // 5. Verifica alerta de adicionado ao carrinho
    cy.get('@alertStub').should('have.been.calledWith', 'Produto adicionado ao carrinho!');

    // 6. Navega para o carrinho
    cy.visit(baseUrl + '/carrinho');

    cy.window().then((win) => {
      cy.stub(win, 'alert').as('alertStub');
    });

    // 8. Clica no botão remover do primeiro item
    cy.get('[data-cy="botao-remover"]').first().click();

    cy.wait(500);

    // 9. Verifica alerta de confirmação de exclusão
    cy.get('@alertStub').should('have.been.calledWith', 'Item removido com sucesso!');
  });
});
