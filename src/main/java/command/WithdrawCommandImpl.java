package command;

import infra.BankAccountJPA;
import infra.MoneyJPA;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;
import service.DepositCalculator;
import service.WithdrawCalculator;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class WithdrawCommandImpl implements WithdrawCommand {
    private static final org.slf4j.Logger LOGGER = getLogger(WithdrawCommandImpl.class);

    private final BankAccountSpringDataRepository bankAccountSpringDataRepository;

    public WithdrawCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository) {
        this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
    }

    @Override
    public BankAccountJPA withdraw(String clientId, MoneyJPA moneyToWithdraw) throws Exception {
        BankAccountJPA bankAccountJPA = retrieveBankAccount(clientId);
        return bankAccountSpringDataRepository.save(WithdrawCalculator.calculate(clientId, moneyToWithdraw, bankAccountJPA));
    }

    private BankAccountJPA retrieveBankAccount(String clientId) throws Exception {
        BankAccountJPA bankAccountJPA;
        try {
            bankAccountJPA = bankAccountSpringDataRepository.findById(clientId).orElseThrow(() -> new Exception("can't retrieve account"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return bankAccountJPA;
    }
}
