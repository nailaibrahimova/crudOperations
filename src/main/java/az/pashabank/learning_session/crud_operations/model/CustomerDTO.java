package az.pashabank.learning_session.crud_operations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @NotNull(message = "ID can not be null")
    @Min(1)
    private Long id;

    @NotNull(message = "Not valid name")
    @Size(min = 2, message = "Name must have minimum 2 letters!")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please, Enter Valid name")
    private String name;
}
