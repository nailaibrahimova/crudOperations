package az.pashabank.learning_sessions.crud_operations_with_database.repository;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
}
