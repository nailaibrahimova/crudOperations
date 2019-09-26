package az.pashabank.learning_session.crud_operations.controller;

import az.pashabank.learning_session.crud_operations.model.CustomerDTO;
import az.pashabank.learning_session.crud_operations.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CustomerDTO getCustomerById(@PathVariable("customerId") Long customer_id) {
        logger.info("Getting customer with id {}", customer_id);
        return customerService.getCustomerById(customer_id);
    }

    @GetMapping("all")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("add")
    public CustomerDTO addCustomer(@Valid @RequestBody CustomerDTO customer) {
        logger.info("Adding new customer with id=" + customer.getId());
        return customerService.addCustomer(customer);
    }

    @PutMapping("update")
    public CustomerDTO updateCustomer(@Valid @RequestBody CustomerDTO customer) {
        logger.info("Updating customer with id=" + customer.getId());
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("delete/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        logger.info("Deleting customer with id=" + customerId);
        customerService.deleteCustomer(customerId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException exp) {
        return exp.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        return new ResponseDTO(1, exp.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
