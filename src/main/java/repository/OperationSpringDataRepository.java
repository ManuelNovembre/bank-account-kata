package repository;

import infra.OperationJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationSpringDataRepository extends JpaRepository<OperationJPA, String> {
}
