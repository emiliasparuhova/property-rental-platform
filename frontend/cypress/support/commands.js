Cypress.Commands.add('login', () => {
    cy.visit('http://localhost:5173/login');
    cy.get('input[name="email"]').type('emilyasparuhova@gmail.com');
    cy.get('input[name="plainTextPassword"]').type('1');
    cy.get('input[type="submit"]').click();
    cy.url().should('eq', 'http://localhost:5173/');
});