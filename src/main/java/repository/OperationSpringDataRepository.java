package repository;

import model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationSpringDataRepository extends JpaRepository<Operation, String> {
}
