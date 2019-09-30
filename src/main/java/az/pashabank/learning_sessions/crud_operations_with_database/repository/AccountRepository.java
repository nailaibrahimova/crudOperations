package az.pashabank.learning_sessions.crud_operations_with_database.repository;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.AccountEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByCustomerEntity(CustomerEntity customerEntity);
}
