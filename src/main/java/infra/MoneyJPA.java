package infra;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@ToString
public class MoneyJPA {

    private Integer euros;
    private Integer cents;

    public MoneyJPA() {
    }

    public MoneyJPA(Integer euros, Integer cents) {
        this.euros = euros;
        this.cents = cents;
    }

    public Integer getEuros() {
        return euros;
    }

    public Integer getCents() {
        return cents;
    }
}
