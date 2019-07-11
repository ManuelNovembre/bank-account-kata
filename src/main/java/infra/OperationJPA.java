package infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
@Getter
@ToString
public class OperationJPA {
	@Id
	private String Id;

	private LocalDate date;
	private String operationType;
	@Embedded
	private MoneyJPA money;
	@ManyToOne
	private BankAccountJPA bankAccount;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OperationJPA that = (OperationJPA) o;
		return Objects.equals(Id, that.Id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(Id);
	}
}
