Feature: make a withdraw

  Scenario Outline: make a withdraw
    Given a bank client "Franck" has <initialEuros>.<initialCents> in is account

    When "Franck" withdraws <withdrawEuros>.<withdrawCents>

    Then "Franck" has <finalEuros>.<finalCents> in his account

#    And the operation is added in his history
    Examples:
      | initialEuros | initialCents | withdrawEuros | withdrawCents | finalEuros | finalCents |
      | 0            | 0            | 0             | 0             | 0          | 0          |
      | 10           | 0            | 10            | 0             | 0          | 0          |
      | 10           | 0            | 5             | 25            | 4          | 75         |
      | 10           | 50           | 5             | 25            | 5          | 25         |
      | 10           | 0            | 20            | 0             | -10        | 0          |
      | 5            | 0            | 10            | 25            | -5         | 25         |