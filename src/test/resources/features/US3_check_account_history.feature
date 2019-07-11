Feature: check account history

  Scenario: get account history
    Given a bank client "Franck" has the following operations saved
      | date       | operationType | euros | cent |
      | 2019-07-11 | Deposit       | 10    | 15   |
      | 2019-07-02 | Withdraw      | 20    | 0    |
      | 2018-07-02 | Deposit       | 15    | 0    |

    When "Franck" check his history

    Then systems returns the following operations
      | date       | operationType | euros | cent |
      | 2019-07-11 | Deposit       | 10    | 15   |
      | 2019-07-02 | Withdraw      | 20    | 0    |
      | 2018-07-02 | Deposit       | 15    | 0    |