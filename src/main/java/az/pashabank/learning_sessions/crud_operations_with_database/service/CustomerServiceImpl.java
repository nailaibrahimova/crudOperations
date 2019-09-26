package az.pashabank.learning_sessions.crud_operations_with_database.service;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.CustomerNotFound;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.NotValidParameters;
import az.pashabank.learning_sessions.crud_operations_with_database.mapper.ModelMapper;
import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final java.util.Random rand = new java.util.Random();
    private final Set<String> identifiers = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Override
    public CustomerDTO getCustomerById(Long id) {
        logger.info("Getting customer with id={}", id);
        CustomerEntity entity = customerRepository.getOne(id);
        return ModelMapper.convertEntityToDto(entity);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Getting list of all customers");
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return customerEntities.stream().map(customerEntity -> ModelMapper.convertEntityToDto(customerEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void addCustomer(String name) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(name);
        logger.info("Adding customer");
        customerRepository.saveAndFlush(customerEntity);
        logger.info("Customer with name={} is added", name);
    }

    @Override
    public void updateCustomer(CustomerDTO customer) {
        logger.info("Updating customer");
        Long id = customer.getId();
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if (customerEntity.isPresent()) {
            String name = ModelMapper.convertDtoToEntity(customer).getName();
            if (name != null) {
                customerEntity.get().setName(name);
                customerRepository.save(customerEntity.get());
                logger.info("Customer with id={} is updated", customerEntity.get().getId());
            }
        } else {
            logger.error("", new CustomerNotFound("No customer with such id"));
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        logger.info("Deleting customer with id={}", id);
        if (id == null) {
            logger.error("", new NotValidParameters("Giver the valid id!!!!! id should  be >=1!!!!"));
        } else {
            Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
            if (customerEntity.isPresent()) {
                customerRepository.deleteById(id);
                logger.info("Customer is deleted");
            } else logger.error("", new CustomerNotFound("No customer with such id"));
        }
    }

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    @Scheduled(fixedRate = 10000)
    public void addRandomCustomer() {
        logger.info("Adding randomly customer to database");
        CustomerEntity customerEntity = new CustomerEntity();

        String name = randomIdentifier();
        customerEntity.setName(name);
        if (customerEntity.getName() != null) {
            customerRepository.saveAndFlush(customerEntity);
            logger.info("Random customer is added");
        }
    }

}
