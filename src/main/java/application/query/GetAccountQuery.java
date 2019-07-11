package application.query;

import model.BankAccount;

public interface GetAccountQuery {
	BankAccount getGetAccountQuery(String clientId) throws Exception;
}
