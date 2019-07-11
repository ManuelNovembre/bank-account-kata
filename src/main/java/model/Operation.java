package model;

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

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "operation")
public class Operation {
	@Id
	private String Id;

	private LocalDate date;
	private String operationType;
	@Embedded
	private Money money;
	@ManyToOne
	private BankAccount bankAccount;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Operation that = (Operation) o;
		return Objects.equals(Id, that.Id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}
}
