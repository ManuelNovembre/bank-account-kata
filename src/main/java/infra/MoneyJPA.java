package infra;

public class MoneyJPA {
    private Integer amount;
    private Integer cents;

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
