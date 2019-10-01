package az.pashabank.learning_sessions.crud_operations_with_database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("AccountDTOFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @NotNull(message = "ID can not be null")
//    @Min(1)
    @Min(value = 1, message = "ID cannot be less than 1")
    private Long id;

    @NotNull(message = "Not valid login")
    @Size(min = 4, message = "Login must have minimum 4 characters!")
    @Pattern(regexp = "^[a-z]*$", message = "Please, Enter Valid login (example: nibrahimova)")
    private String login;

    @NotNull(message = "Not valid password")
    @Size(min = 8, message = "Password must have minimum 8 characters")
    @Pattern(regexp = "[a-zA-Z]+[!@#$%&0-9]+")
    private String password;

//    @Min(0)
    @Min(value = 0, message = "balance cannot be less than 0")
    private double balance;

    @NotNull(message = "Customer ID cannot be null")
//    @Min(1)
    @Min(value = 1, message = "customerId cannot be less than 1")
    private long customerId;
}
