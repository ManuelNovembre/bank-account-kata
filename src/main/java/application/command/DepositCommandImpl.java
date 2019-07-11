package application.command;

import model.BankAccount;
import model.Money;
import model.Operation;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;
import repository.OperationSpringDataRepository;
import service.DepositCalculator;

import java.time.LocalDate;
import java.util.UUID;

import static model.OperationType.DEPIOSIT;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DepositCommandImpl implements DepositCommand {
	private static final org.slf4j.Logger LOGGER = getLogger(DepositCommandImpl.class);

	private final BankAccountSpringDataRepository bankAccountSpringDataRepository;
	private final OperationSpringDataRepository operationSpringDataRepository;

	public DepositCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository, OperationSpringDataRepository operationSpringDataRepository) {
		this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
		this.operationSpringDataRepository = operationSpringDataRepository;
	}

	@Override
	public BankAccount deposit(String clientId, Money moneyToDeposit) throws Exception {
		BankAccount bankAccount = retrieveBankAccount(clientId);

		BankAccount bankAccountToSave = DepositCalculator.calculate(clientId, moneyToDeposit, bankAccount);

		operationSpringDataRepository.save(Operation.builder()
													.Id(UUID.randomUUID().toString())
													.date(LocalDate.now())
													.money(bankAccountToSave.getMoney())
													.operationType(DEPIOSIT.getLabel())
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
