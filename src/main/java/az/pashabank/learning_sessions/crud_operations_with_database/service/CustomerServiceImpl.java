package az.pashabank.learning_sessions.crud_operations_with_database.service;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.CustomerException;
import az.pashabank.learning_sessions.crud_operations_with_database.mapper.CustomerMapper;
import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.repository.CustomerRepository;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Override
    public CustomerDTO getCustomerById(Long id) {
        logger.info("ActionLog.getCustomerById: id={}", id);
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        return CustomerMapper.INSTANCE.convertEntityToDto(customerEntity);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        logger.info("ActionLog.getAllCustomers");
        List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();
        return customerEntities.stream().map(customerEntity -> CustomerMapper.INSTANCE.convertEntityToDto(customerEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void addCustomer(String name) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(name);
        logger.info("ActionLog.addCustomer");
        customerRepository.save(customerEntity);
        logger.info("ActionLog.addCustomer: customer with name={} is added", name);
    }

    @Override
    public void updateCustomer(CustomerDTO customer) {
        logger.info("ActionLog.updateCustomer");
        Long id = customer.getId();
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if (customerEntity.isPresent()) {
            String name = CustomerMapper.INSTANCE.convertDtoToEntity(customer).getName();
            if (name != null) {
                customerEntity.get().setName(name);
                customerRepository.save(customerEntity.get());
                logger.info("ActionLog.updateCustomer: customer with id={} is updated", customerEntity.get().getId());
            }
        } else {
            logger.error("", new CustomerException("No customer with such id"));
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        logger.info("ActionLog.deleteCustomer: id={}", id);
        if (id == null) {
            logger.error("", new CustomerException("Giver the valid id!!!!! id should  be >=1!!!!"));
        } else {
            Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
            if (customerEntity.isPresent()) {
                customerRepository.deleteById(id);
                logger.info("ActionLog.deleteCustomer: customer is deleted");
            } else logger.error("", new CustomerException("No customer with such id"));
        }
    }
}
