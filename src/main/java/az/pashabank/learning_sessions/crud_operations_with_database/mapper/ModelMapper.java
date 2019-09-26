package az.pashabank.learning_sessions.crud_operations_with_database.mapper;


import az.pashabank.learning_sessions.crud_operations_with_database.dao.CustomerEntity;
import az.pashabank.learning_sessions.crud_operations_with_database.model.CustomerDTO;

public class ModelMapper {
    public static CustomerEntity convertDtoToEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        Long id = customerDTO.getId();
        String name = customerDTO.getName();
        customerEntity.setId(id);
        customerEntity.setName(name);
        return customerEntity;
    }

    public static CustomerDTO convertEntityToDto(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        Long id = customerEntity.getId();
        String name = customerEntity.getName();
        customerDTO.setId(id);
        customerDTO.setName(name);
        return customerDTO;
    }
}
