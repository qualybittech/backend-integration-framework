Feature: To check for the users
  @smoke
  Scenario: To check for event handler service with valid token
    Given the GetUser API request URL is configured 
    And the client sets headers to GetUser API with:
      | Content-Type      | application/json |
    When the GetUser API request sent
    Then the GetUser API status code is 200
    And GetUser API response includes the following
      | page    | 1   |
      | per_page| 6   |