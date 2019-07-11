package application.command;

import model.BankAccount;
import model.Money;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateAccountCommandImpl implements CreateAccountCommand {
	private static final org.slf4j.Logger LOGGER = getLogger(CreateAccountCommandImpl.class);

	private final BankAccountSpringDataRepository bankAccountSpringDataRepository;

	public CreateAccountCommandImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository) {
		this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
	}

	@Override
	public BankAccount create(String clientId, Money moneyToDeposit) throws Exception {
		checkIfClientAlreadyHasAnAccount(clientId);

		return bankAccountSpringDataRepository.save(BankAccount.builder()
															   .clientId(clientId)
															   .money(Money.builder()
																		   .cents(moneyToDeposit.getEuros())
																		   .euros(moneyToDeposit.getCents())
																		   .build()).build());
	}

	private void checkIfClientAlreadyHasAnAccount(String clientId) throws Exception {
		try {
			if (bankAccountSpringDataRepository.findById(clientId).isPresent()) {
				throw new Exception("client Already exit");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

}
