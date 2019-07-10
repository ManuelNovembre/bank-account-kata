package infra;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bank_account")
public class BankAccountJPA {
	@Id
	private String clientId;

	@Embedded
	private MoneyJPA money;

	public BankAccountJPA() {
	}

	public BankAccountJPA(String clientId, MoneyJPA money) {
		this.clientId = clientId;
		this.money = money;
	}

	public MoneyJPA getMoney() {
		return money;
	}

	public String getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return "BankAccountJPA{" +
				"clientId='" + clientId + '\'' +
				", money=" + money +
				'}';
	}
}
