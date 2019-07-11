package infra;

import lombok.Builder;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Builder
@Entity(name = "bank_account")
public class BankAccountJPA {
    @Id
    private String clientId;

    @Embedded
    private MoneyJPA money;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OperationJPA> hisory = new ArrayList<>();

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

    public List<OperationJPA> getHisory() {
        return hisory;
    }

    public void setHisory(List<OperationJPA> hisory) {
        if (hisory != null) {
            this.hisory.clear();
            this.hisory.addAll(hisory);
        }
    }

    @Override
    public String toString() {
        return "BankAccountJPA{" +
                "clientId='" + clientId + '\'' +
                ", money=" + money +
                '}';
    }
}
