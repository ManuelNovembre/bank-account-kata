package service;

import model.BankAccount;
import model.Money;

public final class DepositCalculator {

    private static final int ONE_EURO = 100;

    private DepositCalculator() {
    }

    public static BankAccount calculate(String clientId, Money moneyToDeposit, BankAccount bankAccount) {
        Money savedMoney = bankAccount.getMoney();

        int resultAmount = savedMoney.getEuros() + moneyToDeposit.getEuros();
        int resultCent = savedMoney.getCents() + moneyToDeposit.getCents();

        resultAmount = checkIfCentAdditionIsMoreThanOneEuro(resultAmount, resultCent);

        Money resultMoney = new Money(resultAmount, resultCent);
        return new BankAccount(clientId, resultMoney);
    }

    private static int checkIfCentAdditionIsMoreThanOneEuro(int resultAmount, int resultCent) {
        if (resultCent >= ONE_EURO) {
            resultAmount++;
        }
        return resultAmount;
    }
}
