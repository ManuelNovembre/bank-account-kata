package service;

import infra.BankAccountJPA;
import infra.MoneyJPA;

public final class WithdrawCalculator {

    private static final int ONE_EURO = 100;

    private WithdrawCalculator() {
    }

    public static BankAccountJPA calculate(String clientId, MoneyJPA moneyToWithdraw, BankAccountJPA bankAccountJPA) {
        MoneyJPA savedMoney = bankAccountJPA.getMoney();

        int resultAmount = savedMoney.getEuros() - moneyToWithdraw.getEuros();
        int resultCent = savedMoney.getCents() - moneyToWithdraw.getCents();

        MoneyJPA resultMoney = checkIfCentAdditionIsMoreThanOneEuro(resultAmount, resultCent);

        return new BankAccountJPA(clientId, resultMoney);
    }

    private static MoneyJPA checkIfCentAdditionIsMoreThanOneEuro(int resultAmount, int resultCent) {
        if (resultCent < 0 && resultAmount > 0) {
            resultAmount--;
            resultCent = ONE_EURO - Math.abs(resultCent);
        }

        return new MoneyJPA(resultAmount, Math.abs(resultCent));
    }
}
