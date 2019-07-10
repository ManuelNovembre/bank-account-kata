package command;

import exposition.controller.BankController;
import infra.BankAccountJPA;
import infra.MoneyJPA;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DepositCommandImpl implements DepositCommand {
    private static final org.slf4j.Logger LOGGER = getLogger(DepositCommandImpl.class);

    private final BankAccountSpringDataRepository bankAccountSpringDataRepository;

    public DepositCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository) {
        this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
    }

    @Override
    public BankAccountJPA deposit(String clientId, MoneyJPA moneyToDeposit) throws Exception {
        BankAccountJPA bankAccountJPA = null;
        try {
            bankAccountJPA = bankAccountSpringDataRepository.findById(clientId).orElseThrow(() -> new Exception("can't retrieve account"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        MoneyJPA savedMoney = bankAccountJPA.getMoney();

        int resultAmount = savedMoney.getAmount() + moneyToDeposit.getAmount();
        int resultCent = savedMoney.getCents() + moneyToDeposit.getCents();
        if (resultCent >= 100) {
            resultAmount++;
        }
        MoneyJPA resultMoney = new MoneyJPA(resultAmount, resultCent);
        BankAccountJPA resultBankAccount = new BankAccountJPA(clientId, resultMoney);

        return bankAccountSpringDataRepository.save(resultBankAccount);
    }
}
