package service;

import model.BankAccount;
import model.Money;

public final class WithdrawCalculator {

    private static final int ONE_EURO = 100;

    private WithdrawCalculator() {
    }

    public static BankAccount calculate(String clientId, Money moneyToWithdraw, BankAccount bankAccount) {
        Money savedMoney = bankAccount.getMoney();

        int resultAmount = savedMoney.getEuros() - moneyToWithdraw.getEuros();
        int resultCent = savedMoney.getCents() - moneyToWithdraw.getCents();

        Money resultMoney = checkIfCentAdditionIsMoreThanOneEuro(resultAmount, resultCent);

        return new BankAccount(clientId, resultMoney);
    }

    private static Money checkIfCentAdditionIsMoreThanOneEuro(int resultAmount, int resultCent) {
        if (resultCent < 0 && resultAmount > 0) {
            resultAmount--;
            resultCent = ONE_EURO - Math.abs(resultCent);
        }

        return new Money(resultAmount, Math.abs(resultCent));
    }
}
