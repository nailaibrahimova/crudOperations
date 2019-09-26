package az.pashabank.learning_session.crud_operations.repository;

import az.pashabank.learning_session.crud_operations.model.CustomerDTO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Data
public class CustomerRepository {

    private static List<CustomerDTO> customers;

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    public CustomerRepository() {
        customers = new ArrayList<>();
        customers.add(new CustomerDTO(1L, "Naila"));
        customers.add(new CustomerDTO(2L, "Kamal"));
        customers.add(new CustomerDTO(3L, "Sabina"));
    }

    public CustomerDTO getCustomerById(Long id) {
        logger.info("Searching for customer with id=" + id);
        List<CustomerDTO> customer = customers.stream()
                .filter(customer1 -> customer1.getId().equals(id)).collect(Collectors.toList());
        return customer.get(0);
    }

    public List<CustomerDTO> getAllCustomers() {
        logger.info("Returning customer list");
        List<CustomerDTO> sorted = customers.stream().sorted(Comparator.comparing(CustomerDTO::getId))
                .collect(Collectors.toList());
        return sorted;
    }


    public CustomerDTO addCustomer(CustomerDTO customer) {
        logger.info("Adding customer with id=" + customer.getId());
        customers.add(customer);
        return customer;
    }


    public CustomerDTO updateCustomer(CustomerDTO customer) {
        logger.info("Updating customer with id=" + customer.getId());
        customers.removeIf(customer1 -> customer1.getId().equals(customer.getId()));
        customers.add(customer);
        return customer;
    }


    public Long deleteCustomer(Long id) {
        logger.info("Deleting customer with id=" + id);
        CustomerDTO customerDTO = getCustomerById(id);
        customers.removeIf(customer -> customer.getId().equals(id));
        return rewriteIds(id);
    }


    public Long rewriteIds(Long id) {
        if (id <= 3L) {
            customers.stream().forEach(customer -> customer.setId(customer.getId() - 1));
            Long lastId = customers.get(customers.size() - 1).getId() + 1;
            return lastId;
        } else if (id > 3L) {
            Long idToSet = 4L;
            for (CustomerDTO c : customers) {
                if (c.getId() > 3L) {
                    c.setId(idToSet);
                    idToSet++;
                }
            }
            return idToSet;
        }
        return customers.get(customers.size() - 1).getId() + 1;
    }
}
