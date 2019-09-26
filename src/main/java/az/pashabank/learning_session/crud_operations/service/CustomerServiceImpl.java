package az.pashabank.learning_session.crud_operations.service;

import az.pashabank.learning_session.crud_operations.model.CustomerDTO;
import az.pashabank.learning_session.crud_operations.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private Long lastId=4L;

    private final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final java.util.Random rand = new java.util.Random();
    private final Set<String> identifiers = new HashSet<String>();

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        customer.setId(lastId);
        lastId++;
        return customerRepository.addCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customer) {
        return customerRepository.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        lastId = customerRepository.deleteCustomer(id);
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

    @Scheduled(fixedRate = 5000)
    public void addRandomCustomer() {
        logger.info("Adding randomly customers to the list");
        CustomerDTO customer = new CustomerDTO();

        String name = randomIdentifier();
        System.out.println(name);
        customer.setName(name);
        customer.setId(lastId);
        customerRepository.addCustomer(customer);
        lastId++;
    }

}
