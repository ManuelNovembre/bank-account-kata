package application.query;

import model.BankAccount;
import org.springframework.stereotype.Component;
import repository.BankAccountSpringDataRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class GetAccountQueryImpl implements GetAccountQuery {
	private static final org.slf4j.Logger LOGGER = getLogger(GetAccountQueryImpl.class);
	private final BankAccountSpringDataRepository bankAccountSpringDataRepository;

	public GetAccountQueryImpl(BankAccountSpringDataRepository bankAccountSpringDataRepository) {
		this.bankAccountSpringDataRepository = bankAccountSpringDataRepository;
	}

	@Override
	public BankAccount getGetAccountQuery(String clientId) throws Exception {
		try {
			return bankAccountSpringDataRepository.findById(clientId).orElseThrow(() -> new Exception("can't retrieve bank account"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}
}
