package repository;

import infra.BankAccountJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountSpringDataRepository extends JpaRepository<BankAccountJPA, Integer> {
}
