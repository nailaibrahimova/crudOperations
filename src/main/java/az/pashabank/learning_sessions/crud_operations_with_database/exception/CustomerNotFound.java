package az.pashabank.learning_sessions.crud_operations_with_database.exception;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(String message) {
        super(message);
    }
}
