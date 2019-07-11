package command;

import infra.BankAccountJPA;
import infra.MoneyJPA;

public interface WithdrawCommand {
    BankAccountJPA withdraw(String clientId, MoneyJPA moneyToWithdraw) throws Exception;
}
