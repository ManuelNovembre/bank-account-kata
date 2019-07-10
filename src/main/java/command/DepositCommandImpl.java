package command;

import infra.BankAccountJPA;
import infra.MoneyJPA;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;
import service.DepositCalculator;

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
        BankAccountJPA bankAccountJPA = retrieveBankAccount(clientId);
        return bankAccountSpringDataRepository.save(DepositCalculator.calculDeposit(clientId, moneyToDeposit, bankAccountJPA));
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
