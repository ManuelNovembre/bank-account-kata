Feature: make a deposit

  Scenario Outline: make a deposit
    Given a bank client "Franck" has <initialEuros>.<initialCents> in is account

    When "Franck" deposits <depositsEuros>.<depositsCents>

    Then "Franck" has <finalEuros>.<finalCents> in his account

#    And the operation is added in his history
    Examples:
      | initialEuros | initialCents | depositsEuros | depositsCents | finalEuros | finalCents |
      | 0            | 0            | 0             | 0             | 0          | 0          |
      | 0            | 0            | 10            | 0             | 10         | 0          |
      | 10           | 0            | 5             | 25            | 15         | 25         |
      | 10           | 50           | 5             | 25            | 15         | 75         |