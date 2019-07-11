package application.query;

import infra.OperationJPA;

import java.util.List;

public interface GetHistoryQuery {
	List<OperationJPA> getHistoryOperations(String clientId);
}
