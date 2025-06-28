describe('Create Advert', () => {
  beforeEach(() => {
    cy.login();
  });

  it('should allow the user to create an advert', () => {
    cy.visit('http://localhost:5173/create-advert');

    cy.get('input[name="price"]').type('1000');
    cy.get('textarea[name="description"]').type('This is a test advert.');
    cy.get('input[name="size"]').type('120');
    cy.get('input[name="numberOfRooms"]').type('3');   
    cy.get('select[name="propertyType"]').select('Apartment');
    cy.get('select[name="furnishingType"]').select('FURNISHED');
    cy.get('input[name="street"]').type('123 Test Street');
    cy.get('input[name="zipcode"]').type('12345');
    cy.get('#citySelect').click();
    cy.get('#citySelect').type('Amsterdam{enter}');
    cy.get('input[name="utilitiesIncluded"][value="Yes"]').check();
    cy.get('input[name="availableFrom"]').type('2025-01-01');  

    cy.get('button[type="submit"]').click();

    cy.url().should('include', 'http://localhost:5173/my-adverts');
    cy.contains('Advert created').should('exist');
  });

  it('should not allow the user to create an advert when street is empty', () => {
    cy.visit('http://localhost:5173/create-advert');

    cy.get('input[name="price"]').type('1000');
    cy.get('textarea[name="description"]').type('This is a test advert.');
    cy.get('input[name="size"]').type('120');
    cy.get('input[name="numberOfRooms"]').type('3');   
    cy.get('select[name="propertyType"]').select('Apartment');
    cy.get('select[name="furnishingType"]').select('FURNISHED');
    cy.get('input[name="street"]').clear();
    cy.get('input[name="zipcode"]').type('12345');
    cy.get('#citySelect').click();
    cy.get('#citySelect').type('Amsterdam{enter}');
    cy.get('input[name="utilitiesIncluded"][value="Yes"]').check();
    cy.get('input[name="availableFrom"]').type('2025-01-01');  

    cy.get('button[type="submit"]').click();

    cy.url().should('not.include', 'http://localhost:5173/my-adverts'); 
    cy.contains('Advert created').should('not.exist'); 
    cy.contains('The street field is mandatory').should('exist'); 
  });
});
