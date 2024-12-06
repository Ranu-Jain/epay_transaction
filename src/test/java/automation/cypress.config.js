const { defineConfig } = require('cypress');
const createBundler = require('@bahmutov/cypress-esbuild-preprocessor');
const addCucumberPreprocessorPlugin = require('@badeball/cypress-cucumber-preprocessor').addCucumberPreprocessorPlugin;
const { createEsbuildPlugin } = require('@badeball/cypress-cucumber-preprocessor/esbuild'); // Correct import

module.exports = defineConfig({
  e2e: {
    async setupNodeEvents(on, config) {
      // Add cucumber preprocessor plugin
      await addCucumberPreprocessorPlugin(on, config);

      // Set up esbuild bundler
      on(
        'file:preprocessor',
        createBundler({
          plugins: [createEsbuildPlugin(config)],
        })
      );

      return config;
    },
    specPattern: 'cypress/e2e/features/*.feature',
    baseUrl: 'https://cms.dev.electrolitemobility.com', // Update this to match your app's base URL
    stepDefinitions: 'cypress/e2e/step_definitions/*.js'
  },
});
