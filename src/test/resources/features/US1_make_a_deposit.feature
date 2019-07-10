Feature: make a deposit

  Scenario Outline: make a deposit
    Given a bank client "Franck" has <initialAmount>.<initialCents> in is account

    When "Franck" deposits <depositsAmount>.<depositsCents>

    Then "Franck" has <finalAmount>.<finalCents> in his account

#    And the operation is added in his history
    Examples:
      | initialAmount | initialCents | depositsAmount | depositsCents | finalAmount | finalCents |
      | 0             | 0            | 0              | 0             | 0           | 0          |
      | 0             | 0            | 10             | 0             | 10          | 0          |
      | 10            | 0            | 5              | 25            | 15          | 25         |
      | 10            | 50           | 5              | 25            | 15          | 75         |