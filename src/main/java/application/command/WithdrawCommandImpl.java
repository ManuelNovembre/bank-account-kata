package application.command;

import infra.BankAccountJPA;
import infra.MoneyJPA;
import infra.OperationJPA;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;
import service.WithdrawCalculator;

import java.time.LocalDate;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class WithdrawCommandImpl implements WithdrawCommand {
    private static final org.slf4j.Logger LOGGER = getLogger(WithdrawCommandImpl.class);
    private static final String WITHDRAW_OPERATION_TYPE = "Withdraw";

    private final BankAccountSpringDataRepository bankAccountSpringDataRepository;
    private final OperationSpringDataRepository operationSpringDataRepository;

    public WithdrawCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository, OperationSpringDataRepository operationSpringDataRepository) {
        this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
        this.operationSpringDataRepository = operationSpringDataRepository;
    }

    @Override
    public BankAccountJPA withdraw(String clientId, MoneyJPA moneyToWithdraw) throws Exception {
        BankAccountJPA bankAccountJPA = retrieveBankAccount(clientId);

        final BankAccountJPA bankAccountToSave = WithdrawCalculator.calculate(clientId, moneyToWithdraw, bankAccountJPA);
        operationSpringDataRepository.save(OperationJPA.builder()
                .Id(UUID.randomUUID().toString())
                .date(LocalDate.now())
                .money(bankAccountToSave.getMoney())
                .operationType(WITHDRAW_OPERATION_TYPE)
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
