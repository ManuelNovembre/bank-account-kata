package application.command;

import model.BankAccount;
import model.Money;
import model.Operation;
import model.OperationType;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;
import service.WithdrawCalculator;

import java.time.LocalDate;
import java.util.UUID;

import static model.OperationType.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class WithdrawCommandImpl implements WithdrawCommand {
    private static final org.slf4j.Logger LOGGER = getLogger(WithdrawCommandImpl.class);

    private final BankAccountSpringDataRepository bankAccountSpringDataRepository;
    private final OperationSpringDataRepository operationSpringDataRepository;

    public WithdrawCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository, OperationSpringDataRepository operationSpringDataRepository) {
        this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
        this.operationSpringDataRepository = operationSpringDataRepository;
    }

    @Override
    public BankAccount withdraw(String clientId, Money moneyToWithdraw) throws Exception {
        BankAccount bankAccount = retrieveBankAccount(clientId);

        final BankAccount bankAccountToSave = WithdrawCalculator.calculate(clientId, moneyToWithdraw, bankAccount);
        operationSpringDataRepository.save(Operation.builder()
                                                    .Id(UUID.randomUUID().toString())
                                                    .date(LocalDate.now())
                                                    .money(bankAccountToSave.getMoney())
                                                    .operationType(WITHDRAW.getLabel())
                                                    .build());

        return bankAccountSpringDataRepository.save(bankAccountToSave);
    }

    private BankAccount retrieveBankAccount(String clientId) throws Exception {
        BankAccount bankAccount;
        try {
            bankAccount = bankAccountSpringDataRepository.findById(clientId).orElseThrow(() -> new Exception("can't retrieve account"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return bankAccount;
    }
}
