Feature: check acount history

  Scenario : get account history
    Given a bank client "Franck" has the following operations saved
      | Date       | Operation | euros | cent |
      | 2019-07-11 | Deposit   | 10    | 15   |
      | 2019-07-02 | WithDraw  | 20    | 0    |
      | 2018-07-02 | Deposit   | 15    | 0    |

    When "Franck" check his history

    Then systems returns the following operations
      | Date       | Operation | euros | cent |
      | 2019-07-11 | Deposit   | 10    | 15   |
      | 2019-07-02 | WithDraw  | 20    | 0    |
      | 2018-07-02 | Deposit   | 15    | 0    |