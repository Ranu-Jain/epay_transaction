Feature: Verify the title of the page

  Scenario: Visit the homepage and check the title
    Given I open the homepage
    Then the page title should be "Electrolite"
