package application.command;

import model.BankAccount;
import model.Money;

public interface CreateAccountCommand {
    BankAccount create(String clientId, Money moneyToDeposit) throws Exception;
}
