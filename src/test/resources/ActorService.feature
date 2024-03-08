Feature: Actor

  Scenario: Actor is deleted when not in db
    Given actor with id 1 does not exist in db
    When delete method is called with id 1
    Then a response status exception is thrown
