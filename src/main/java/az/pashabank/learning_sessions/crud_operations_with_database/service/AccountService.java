package az.pashabank.learning_sessions.crud_operations_with_database.service;

import az.pashabank.learning_sessions.crud_operations_with_database.model.AccountDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;

import java.util.List;

public interface AccountService {
    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAllAccounts();

    List<AccountDTO> getAllAccountsByCustomerId(Long customerId);

    void addAccount(AccountDTO accountDTO);

    void updateAccount(AccountDTO customer);

    void deleteAccount(Long id);
}
