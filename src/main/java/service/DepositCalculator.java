package service;

import infra.BankAccountJPA;
import infra.MoneyJPA;

public final class DepositCalculator {

    private static final int ONE_EURO = 100;

    private DepositCalculator() {
    }

    public static BankAccountJPA calculate(String clientId, MoneyJPA moneyToDeposit, BankAccountJPA bankAccountJPA) {
        MoneyJPA savedMoney = bankAccountJPA.getMoney();

        int resultAmount = savedMoney.getEuros() + moneyToDeposit.getEuros();
        int resultCent = savedMoney.getCents() + moneyToDeposit.getCents();

        resultAmount = checkIfCentAdditionIsMoreThanOneEuro(resultAmount, resultCent);

        MoneyJPA resultMoney = new MoneyJPA(resultAmount, resultCent);
        return new BankAccountJPA(clientId, resultMoney);
    }

    private static int checkIfCentAdditionIsMoreThanOneEuro(int resultAmount, int resultCent) {
        if (resultCent >= ONE_EURO) {
            resultAmount++;
        }
        return resultAmount;
    }
}
