package infra;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bank_account")
public class BankAccountJPA {
	@Id
	private String clientName;

	@Embedded
	private MoneyJPA money;

	public BankAccountJPA() {
	}

	public BankAccountJPA(String clientName, MoneyJPA money) {
		this.clientName = clientName;
		this.money = money;
	}

	public MoneyJPA getMoney() {
		return money;
	}

	public String getClientName() {
		return clientName;
	}
}
