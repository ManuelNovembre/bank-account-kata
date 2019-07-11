package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "bank_account")
public class BankAccount {
	@OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Operation> hisory = new ArrayList<>();
	@Id
	private String clientId;
	@Embedded
	private Money money;
}
