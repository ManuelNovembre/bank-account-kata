package exposition.dto;

public class BankRequestDTO {
	private Integer money;
	private Integer cents;

	public BankRequestDTO(Integer money, Integer cents) {
		this.money = money;
		this.cents = cents;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getCents() {
		return cents;
	}

	public void setCents(Integer cents) {
		this.cents = cents;
	}
}
