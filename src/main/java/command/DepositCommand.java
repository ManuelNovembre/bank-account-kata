package command;

import infra.BankAccountJPA;
import infra.MoneyJPA;

public interface DepositCommand {
    BankAccountJPA deposit(String clientId, MoneyJPA moneyToDeposit) throws Exception;
}
