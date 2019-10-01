package az.pashabank.learning_sessions.crud_operations_with_database.service;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.AccountEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.AccountException;
import az.pashabank.learning_sessions.crud_operations_with_database.exception.CustomerException;
import az.pashabank.learning_sessions.crud_operations_with_database.mapper.AccountMapper;
import az.pashabank.learning_sessions.crud_operations_with_database.model.AccountDTO;
import az.pashabank.learning_sessions.crud_operations_with_database.repository.AccountRepository;
import az.pashabank.learning_sessions.crud_operations_with_database.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Override
    public AccountDTO getAccountById(Long id) {
        logger.info("ActionLog.getAccountById: id={}", id);
        AccountEntity accountEntity = accountRepository.findById(id).get();
        return AccountMapper.INSTANCE.convertEntityToDto(accountEntity);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        logger.info("ActionLog.getAllAccounts");
        List<AccountEntity> accountEntities = (List<AccountEntity>) accountRepository.findAll();
        return accountEntities.stream().map(accountEntity -> AccountMapper.INSTANCE.convertEntityToDto(accountEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAllAccountsByCustomerId(Long customerId) {
        logger.info("ActionLog.getAllAccountsByCustomerId: id={}", customerId);
        if (!customerRepository.existsById(customerId)) {
            CustomerException exception = new CustomerException("ActionLog.getAllAccountsBtCustomerId: no customer with id=" + customerId);
            logger.error("", exception);
            throw exception;
        } else {
            Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
            if (customerEntity.isPresent()) {
                return accountRepository.findByCustomerEntity(customerEntity.get()).stream()
                        .map(accountEntity -> AccountMapper.INSTANCE.convertEntityToDto(accountEntity))
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public void addAccount(AccountDTO accountDTO) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity = AccountMapper.INSTANCE.convertDtoToEntityCreate(accountDTO);
        logger.info("ActionLog.addAccount");
        accountRepository.save(accountEntity);
        logger.info("ActionLog.addAccount: account with login={} is added", accountDTO.getLogin());
    }

    @Override
    public void updateAccount(AccountDTO account) {
        logger.info("ActionLog.updateAccount");
        Long id = account.getId();
        Optional<AccountEntity> accountEntity = accountRepository.findById(id);
        if (accountEntity.isPresent()) {
            String login = AccountMapper.INSTANCE.convertDtoToEntityUpdate(account).getLogin();
            String password = AccountMapper.INSTANCE.convertDtoToEntityUpdate(account).getPassword();
            if (login != null) {
                accountEntity.get().setLogin(login);
//                accountRepository.save(accountEntity.get());
                logger.info("ActionLog.updateAccount: login of account with id={} is updated", accountEntity.get().getId());
            }
            if (password != null) {
                accountEntity.get().setPassword(password);
//                accountRepository.save(accountEntity.get());
                logger.info("ActionLog.updateAccount: password of account with id={} is updated", accountEntity.get().getId());
            }
            accountEntity.get().setBalance(account.getBalance());
            accountRepository.save(accountEntity.get());
        } else {
            AccountException exception = new AccountException("ActionLog.updateAccount: no account with id=" + account.getId());
            logger.error("", exception);
            throw exception;
        }
    }

    @Override
    public void deleteAccount(Long id) {
        logger.info("ActionLog.deleteAccount: id={}", id);
        if (id == null) {
            logger.error("", new AccountException("ActionLog.deleteAccount: id should  be >=1!!!!"));
        } else {
            Optional<AccountEntity> accountEntity = accountRepository.findById(id);
            if (accountEntity.isPresent()) {
                accountRepository.deleteById(id);
                logger.info("ActionLog.deleteAccount: account is deleted");
            } else {
                AccountException exception = new AccountException("ActionLog.deleteAccount: no account with id=" + id);
                logger.error("", exception);
                throw exception;
            }
        }
    }

    @Override
    public void increaseBalance(Long customerId, Long accountId, double increaseBy) {
        if (customerRepository.existsById(customerId)) {
            Optional<AccountEntity> account = accountRepository.findById(accountId);
            account.ifPresent(accountEntity -> {
                double balance = accountEntity.getBalance() + increaseBy;
                accountEntity.setBalance(balance);
                accountRepository.save(accountEntity);
            });
        } else {
            CustomerException exception = new CustomerException("ActionLog.increaseBalance: no customer with id=" + customerId);
            logger.error("", exception);
            throw exception;
        }
    }

    @Override
    public void decreaseBalance(Long customerId, Long accountId, double decreaseBy) {
        Optional<CustomerEntity> entity = customerRepository.findById(customerId);
        if (entity.isPresent()) {
            Optional<AccountEntity> account = accountRepository.findById(accountId);
            account.ifPresent(accountEntity -> {
                double balance = accountEntity.getBalance() - decreaseBy;
                if(balance>=0){
                    accountEntity.setBalance(balance);
                    accountRepository.save(accountEntity);
                } else {
                    AccountException exception = new AccountException("ActionLog.decreaseBalance: balance after decreasing<0.Not valid!!!");
                    logger.error("", exception);
                    throw exception;
                }
            });
        } else {
            CustomerException exception = new CustomerException("ActionLog.increaseBalance: no customer with id=" + customerId);
            logger.error("", exception);
            throw exception;
        }
    }
}
