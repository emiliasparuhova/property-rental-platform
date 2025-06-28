describe('Update User Profile', () => {
    beforeEach(() => {
      cy.login();
    });
  
    it('should update the city', () => {
        cy.visit('http://localhost:5173/account');
        cy.wait(1000);

        cy.get('input[name="city"]').clear()
        cy.get('input[name="city"]').type("Eindhoven");
        cy.get('button[type="submit"]').click();
        cy.contains('Information successfully updated').should('exist');
    });
  
    it('should fail when leaving the name field empty', () => {
        cy.visit('http://localhost:5173/account');
        cy.wait(1000);

        cy.get('input[name="name"]').clear();
        cy.get('button[type="submit"]').click();
        cy.contains('The name field is mandatory').should('exist');
    });
  });
  