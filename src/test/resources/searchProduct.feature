Feature: Test a successful product search
  Scenario: Search Product
    Given that the customer is on the Home page
    And enters a product name in the search field
    When the customer clicks the search icon to search
    Then the system should return a list of search results