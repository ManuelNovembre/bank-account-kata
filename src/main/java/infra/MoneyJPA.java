package infra;

import javax.persistence.Embeddable;

@Embeddable
public class MoneyJPA {

	private Integer amount;
	private Integer cents;

	public MoneyJPA() {
	}

	public MoneyJPA(Integer amount, Integer cents) {
		this.amount = amount;
		this.cents = cents;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getCents() {
		return cents;
	}
}
