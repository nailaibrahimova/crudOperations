package az.pashabank.learning_sessions.crud_operations_with_database.service;

import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> getAllCustomers();

    void addCustomer(String name);

    void updateCustomer(CustomerDTO customer);

    void deleteCustomer(Long id);
}
