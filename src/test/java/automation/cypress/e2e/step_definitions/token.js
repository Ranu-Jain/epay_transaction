const { Given, Then } = require('@badeball/cypress-cucumber-preprocessor');

// Step definition for opening the homepage
Given('I open the homepage', () => {
  cy.visit('https://cms.dev.electrolitemobility.com/');
});

// Step definition for checking the page title
Then('the page title should be {string}', (expectedTitle) => {
  cy.title().should('eq', expectedTitle);
});


//epay_transaction/src/test/java/automation/cypress/e2e/step_definitions/token.js