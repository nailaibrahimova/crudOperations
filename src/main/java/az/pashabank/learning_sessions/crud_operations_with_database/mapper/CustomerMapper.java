package az.pashabank.learning_sessions.crud_operations_with_database.mapper;

import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerEntity convertDtoToEntity(CustomerDTO customerDTO);

    CustomerDTO convertEntityToDto(CustomerEntity customerEntity);
}
