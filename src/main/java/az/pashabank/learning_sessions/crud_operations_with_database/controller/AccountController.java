package az.pashabank.learning_sessions.crud_operations_with_database.controller;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.mapper.CustomerMapper;
import az.pashabank.learning_sessions.crud_operations_with_database.model.AccountDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.repository.CustomerRepository;
import az.pashabank.learning_sessions.crud_operations_with_database.service.AccountService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/account")
public class AccountController {

    private AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{accountId}")
    public MappingJacksonValue getAccountById(@PathVariable Long accountId) {
        logger.info("ActionLog.getAccountById: id={}", accountId);
        AccountDTO accountDTO = accountService.getAccountById(accountId);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "login");
        FilterProvider filters = new SimpleFilterProvider().addFilter("AccountDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(accountDTO);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("all")
    public MappingJacksonValue getAllAccounts() {
        logger.info("ActionLog.getAllAccounts");
        List<AccountDTO> accountList = accountService.getAllAccounts();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "login");
        FilterProvider filters = new SimpleFilterProvider().addFilter("AccountDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(accountList);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/customerId/{customerId}")
    public MappingJacksonValue getAllAccountsByCustomerId(@PathVariable Long customerId){
        logger.info("ActionLog.getAllAccountsByCustomerId");
        List<AccountDTO> accountList = accountService.getAllAccountsByCustomerId(customerId);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "login");
        FilterProvider filters = new SimpleFilterProvider().addFilter("AccountDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(accountList);
        mapping.setFilters(filters);

        return mapping;
    }

    @PostMapping("add")
    public void addAccount(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("Adding new account");
        accountService.addAccount(accountDTO);
    }

    @PutMapping("update")
    public void updateAccount(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("Updating account with id={}", accountDTO.getId());
        accountService.updateAccount(accountDTO);
    }

    @DeleteMapping("delete/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        logger.info("Deleting account with id={}", accountId);
        accountService.deleteAccount(accountId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException exp) {
        logger.error("", exp.getMessage());
        return exp.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}