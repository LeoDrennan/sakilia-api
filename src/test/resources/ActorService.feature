Feature: Actor

  Scenario: A new film is being added to an actor
    Given the actor id 1 is provided
    When the actor is not already credited for the film
    Then the actors list of films contains id 3