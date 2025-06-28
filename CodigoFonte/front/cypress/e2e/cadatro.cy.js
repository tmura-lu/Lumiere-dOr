describe('Cadastro de usuário', () => {
  const baseUrl = 'http://localhost:5173';

  it('Não permite cadastro com email duplicado', () => {
    const emailDuplicado = `teste${Date.now()}@exemplo.com`;

    // Stub para capturar alertas
    cy.visit(baseUrl + '/login');
    const stub = cy.stub();
    cy.on('window:alert', stub);

    // 1º cadastro
    cy.contains('Cadastre-se').click();

    cy.get('input[placeholder="Digite seu nome completo"]').type('Teste Cypress');
    cy.get('input[placeholder="Digite seu telefone no formato: (99) 9 9999-9999"]').type('(99) 9 9999-9999');
    cy.get('input[placeholder="Digite seu cpf no formato: 000.000.000-00"]').type('123.456.789-00');
    cy.get('input[placeholder="Digite seu e-mail"]').type(emailDuplicado);
    cy.get('input[type="password"]').type('senha123');

    cy.contains('Registrar').click();

    // Espera o primeiro alert com sucesso
    cy.wrap(stub).should('be.calledWith', 'Registrado com sucesso!');

    // Aguarda a página resetar (exemplo: reload)
    cy.reload();

    // 2º cadastro com mesmo email
    cy.contains('Cadastre-se').click();

    cy.get('input[placeholder="Digite seu nome completo"]').type('Teste Cypress');
    cy.get('input[placeholder="Digite seu telefone no formato: (99) 9 9999-9999"]').type('(99) 9 9999-9999');
    cy.get('input[placeholder="Digite seu cpf no formato: 000.000.000-00"]').type('123.456.789-00');
    cy.get('input[placeholder="Digite seu e-mail"]').type(emailDuplicado);
    cy.get('input[type="password"]').type('senha123');

    cy.contains('Registrar').click();

    // Espera o alerta de erro de email duplicado
    cy.wrap(stub).should('be.calledWith', 'Internal Server Error');
  });
});
