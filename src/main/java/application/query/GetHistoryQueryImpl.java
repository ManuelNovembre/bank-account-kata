package application.query;

import infra.BankAccountJPA;
import infra.OperationJPA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import repository.OperationSpringDataRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GetHistoryQueryImpl implements GetHistoryQuery {
	private final OperationSpringDataRepository operationSpringDataRepository;

	public GetHistoryQueryImpl(OperationSpringDataRepository operationSpringDataRepository) {
		this.operationSpringDataRepository = operationSpringDataRepository;
	}

	@Override
	public List<OperationJPA> getHistoryOperations(String clientIdRequest) {
		final List<OperationJPA> allHistory = operationSpringDataRepository.findAll();

		return allHistory.stream()
						 .filter(e -> {
							 final String clientId = Optional.ofNullable(e).map(OperationJPA::getBankAccount)
															 .map(BankAccountJPA::getClientId)
															 .orElse(null);
							 return StringUtils.equals(clientIdRequest, clientId);
						 })
						 .collect(Collectors.toList());

	}
}
