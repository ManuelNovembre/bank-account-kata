package application.command;

import infra.BankAccountJPA;
import infra.MoneyJPA;
import infra.OperationJPA;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;
import service.DepositCalculator;

import java.time.LocalDate;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DepositCommandImpl implements DepositCommand {
    private static final org.slf4j.Logger LOGGER = getLogger(DepositCommandImpl.class);
    private static final String DEPOSIT_OPERATION_TYPE = "Deposit";

    private final BankAccountSpringDataRepository bankAccountSpringDataRepository;
    private final OperationSpringDataRepository operationSpringDataRepository;

    public DepositCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository, OperationSpringDataRepository operationSpringDataRepository) {
        this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
        this.operationSpringDataRepository = operationSpringDataRepository;
    }

    @Override
    public BankAccountJPA deposit(String clientId, MoneyJPA moneyToDeposit) throws Exception {
        BankAccountJPA bankAccountJPA = retrieveBankAccount(clientId);

        BankAccountJPA bankAccountToSave = DepositCalculator.calculate(clientId, moneyToDeposit, bankAccountJPA);

        operationSpringDataRepository.save(OperationJPA.builder()
                .Id(UUID.randomUUID().toString())
                .date(LocalDate.now())
                .money(bankAccountToSave.getMoney())
                .operationType(DEPOSIT_OPERATION_TYPE)
                .build());

        return bankAccountSpringDataRepository.save(bankAccountToSave);
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
