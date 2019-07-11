package infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "operation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationJPA {
    @Id

    private String Id;

    private LocalDate date;
    private String operationType;
    @Embedded
    private MoneyJPA moneyJPA;
    @ManyToOne
    private BankAccountJPA bankAccount;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public MoneyJPA getMoneyJPA() {
        return moneyJPA;
    }

    public void setMoneyJPA(MoneyJPA moneyJPA) {
        this.moneyJPA = moneyJPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationJPA that = (OperationJPA) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(Id);
    }
}
