package az.pashabank.learning_sessions.crud_operations_with_database.controller;

import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomerById(@Min(value = 1, message = "customerId cannot be less than 1")
                                           @PathVariable("customerId") Long customerId) {
        logger.info("Getting customer with id={}", customerId);
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("all")
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Getting list of all customers");
        return customerService.getAllCustomers();
    }

    @PostMapping("add/{customerName}")
    public void addCustomer(@PathVariable("customerName") String name) {
        logger.info("Adding new customer");
        customerService.addCustomer(name);
    }

    @PutMapping("update")
    public void updateCustomer(@Valid @RequestBody CustomerDTO customer) {
        logger.info("Updating customer with id={}", customer.getId());
        customerService.updateCustomer(customer);
    }

    @DeleteMapping("delete/{customerId}")
    public void deleteCustomer(@Min(value = 1, message = "customerId cannot be less than 1")
                                   @PathVariable Long customerId) {
        logger.info("Deleting customer with id={}", customerId);
        customerService.deleteCustomer(customerId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException exp) {
        logger.error("", exp.getMessage());
        return exp.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
