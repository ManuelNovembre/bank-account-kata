package infra;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BankAccountJPA {
    @Id
    private Integer id;
    private  MoneyJPA money;
    private String clientName;

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
