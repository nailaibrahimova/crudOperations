package az.pashabank.learning_sessions.crud_operations_with_database.mapper;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.AccountEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.model.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountEntity convertDtoToEntityCreate(AccountDTO accountDTO);

    AccountEntity convertDtoToEntityUpdate(AccountDTO accountDTO);

    AccountDTO convertEntityToDto(AccountEntity accountEntity);
}
