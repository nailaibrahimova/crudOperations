package az.pashabank.learning_sessions.crud_operations_with_database.controller;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.AccountException;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.CustomerException;
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
import javax.validation.constraints.Min;
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
    public MappingJacksonValue getAccountById(@Min(value = 1, message = "accountId cannot be less than 1")
                                                  @PathVariable Long accountId) {
        logger.info("ActionLog.getAccountById: id={}", accountId);
        AccountDTO accountDTO = accountService.getAccountById(accountId);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "login", "balance");
        FilterProvider filters = new SimpleFilterProvider().addFilter("AccountDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(accountDTO);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("all")
    public MappingJacksonValue getAllAccounts() {
        logger.info("ActionLog.getAllAccounts");
        List<AccountDTO> accountList = accountService.getAllAccounts();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "login", "balance");
        FilterProvider filters = new SimpleFilterProvider().addFilter("AccountDTOFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(accountList);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/getAccountsByCustomerId/{customerId}")
    public MappingJacksonValue getAllAccountsByCustomerId(@Min(value = 1, message = "customerId cannot be less than 1")
                                                              @PathVariable Long customerId) {
        logger.info("ActionLog.getAllAccountsByCustomerId");
        try {
            List<AccountDTO> accountList = accountService.getAllAccountsByCustomerId(customerId);

            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "login", "balance");
            FilterProvider filters = new SimpleFilterProvider().addFilter("AccountDTOFilter", filter);
            MappingJacksonValue mapping = new MappingJacksonValue(accountList);
            mapping.setFilters(filters);

            return mapping;
        } catch (CustomerException e) {
            logger.error("", e);
            return null;
        }
    }

    @PostMapping("add")
    public void addAccount(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("ActionLog.addAccount");
        accountService.addAccount(accountDTO);
    }

    @PutMapping("update")
    public void updateAccount(@Valid @RequestBody AccountDTO accountDTO) {
        logger.info("ActionLog.updateAccount: id={}", accountDTO.getId());
        try {
            accountService.updateAccount(accountDTO);
        } catch (AccountException e) {
            logger.error("", e);
        }
    }

    @DeleteMapping("delete/{accountId}")
    public void deleteAccount(@Min(value = 1, message = "accountId cannot be less than 1")
                                  @PathVariable Long accountId) {
        logger.info("ActionLog.deleteAccount: id={}", accountId);
        try {
            accountService.deleteAccount(accountId);
        } catch (AccountException e) {
            logger.error("", e);
        }
    }

    @GetMapping("{customerId}/increaseBalance/{accountId}/by/{increaseBy}")
    public void increaseBalance(@Min(value = 1, message = "customerId cannot be less than 1") @PathVariable Long customerId,
                                @Min(value = 1, message = "accountId cannot be less than 1") @PathVariable Long accountId,
                                @Min(value = 0, message = "increaseBy cannot be less than 0") @PathVariable double increaseBy) {
        logger.info("ActionLog.increaseBalance: customerId={}, accountId={}, increaseBy{}", customerId, accountId, increaseBy);
        try {
            accountService.increaseBalance(customerId, accountId, increaseBy);
        } catch (AccountException e) {
            logger.error("", e);
        } catch (CustomerException e) {
            logger.error("", e);
        }
    }

    @GetMapping("{customerId}/decreaseBalance/{accountId}/by/{decreaseBy}")
    public void decreaseBalance(@Min(value = 1, message = "customerId cannot be less than 1") @PathVariable Long customerId,
                                @Min(value = 1, message = "accountId cannot be less than 1") @PathVariable Long accountId,
                                @Min(value = 0, message = "decreaseBy cannot be less than 0") @PathVariable double decreaseBy) {
        logger.info("ActionLog.increaseBalance: customerId={}, accountId={}, increaseBy{}", customerId, accountId, decreaseBy);
        try {
            accountService.decreaseBalance(customerId, accountId, decreaseBy);
        } catch (CustomerException e) {
            logger.error("", e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException exp) {
        logger.error("", exp.getMessage());
        return exp.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
