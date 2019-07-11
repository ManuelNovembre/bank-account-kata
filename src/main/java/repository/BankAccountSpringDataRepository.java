package repository;

import infra.BankAccountJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BankAccountSpringDataRepository extends JpaRepository<BankAccountJPA, String> {

}
