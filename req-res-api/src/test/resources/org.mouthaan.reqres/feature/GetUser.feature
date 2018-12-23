Feature: Get user by id

  Scenario: User calls web service to get a user by its id
    Given a user exists with an id 2
    When a user retrieves the user by id
    Then the status code is 200
    And the status line is "HTTP/1.1 200 OK"
    And the response header include the following
      | Content-Type     | application/json; charset=utf-8 |
      | Server           | cloudflare                      |
      | Content-Encoding | gzip                            |
      | Connection       | keep-alive                      |
    And response includes the following
      | data.id         | 2                                                                  |
      | data.first_name | Janet                                                              |
      | data.last_name  | Weaver                                                             |
      | data.avatar     | https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg |