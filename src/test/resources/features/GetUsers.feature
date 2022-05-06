Feature: To check for the users
 
 @smoke   
  Scenario: To check for Get user API with valid data
    Given the BaseURI endpoint is configured 
    And the client sets headers to API with:
      | Content-Type      | application/json |
    When the GetUser API request sent
    Then the API status code is 200
    And API response includes the following
      | page    | 1   |
      | per_page| 6   |
  
  @smoke1   
  Scenario: To check for Post user API with valid data
    Given the BaseURI endpoint is configured 
    And the client sets headers to API with:
      | Content-Type      | application/json |
   And the request body is configured
   		| name     | Raj  |
   		| job      | lead |
    When the PostUser API request sent
    Then the API status code is 201
    And API response includes the following
      |createdAt| 2022-01-27 |
  
  @smoke    
  Scenario: To check for Post user API with valid data
    Given the BaseURI endpoint is configured 
    And the client sets headers to API with:
      | Content-Type      | application/json |
   And the PostUser API request sent
   When the PostUser API request using Pojo model sent
   Then the API status code is 201