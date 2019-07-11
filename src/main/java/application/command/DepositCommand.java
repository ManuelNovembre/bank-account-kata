package application.command;

import model.BankAccount;
import model.Money;

public interface DepositCommand {
    BankAccount deposit(String clientId, Money moneyToDeposit) throws Exception;
}
