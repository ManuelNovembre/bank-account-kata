package repository;

import model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountSpringDataRepository extends JpaRepository<BankAccount, String> {

}
