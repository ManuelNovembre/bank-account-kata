package application.query;

import model.Operation;

import java.util.List;

public interface GetHistoryQuery {
	List<Operation> getHistoryOperations(String clientId);
}
