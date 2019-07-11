package application.command;

import model.BankAccount;
import model.Money;

public interface WithdrawCommand {
    BankAccount withdraw(String clientId, Money moneyToWithdraw) throws Exception;
}
