Feature: get User by Id

  Scenario: User calls web service to get a user by its id
    Given a user exists with an id of "1"
    When a user retrieves the user by id
    Then the status code is 200
    And response includes the following
      | id       | 1                 |
      | username | Bret              |
      | email    | Sincere@april.biz |

  Scenario: User calls web service to get a user by its id
    Given a user exists with an id of "2"
    When a user retrieves the user by id
    Then the status code is 200
    And response includes the following
      | id       | 2                 |
      | username | Antonette         |
      | email    | Shanna@melissa.tv |