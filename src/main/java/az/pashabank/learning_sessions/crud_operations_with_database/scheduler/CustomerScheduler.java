package az.pashabank.learning_sessions.crud_operations_with_database.scheduler;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.repository.CustomerRepository;
import az.pashabank.learning_sessions.crud_operations_with_database.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableScheduling
public class CustomerScheduler {
    private CustomerRepository customerRepository;

    private final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final java.util.Random rand = new java.util.Random();
    private final Set<String> identifiers = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(CustomerScheduler.class);

    public CustomerScheduler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
        logger.info("ActionLog.addRandomCustomer");
        CustomerEntity customerEntity = new CustomerEntity();

        String name = randomIdentifier();
        customerEntity.setName(name);
        if (customerEntity.getName() != null) {
            customerRepository.save(customerEntity);
            logger.info("ActionLog.addRandomCustomer: random customer is added");
        }
    }
}
