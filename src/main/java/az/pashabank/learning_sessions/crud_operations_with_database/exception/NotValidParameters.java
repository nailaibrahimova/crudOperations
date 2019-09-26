package az.pashabank.learning_sessions.crud_operations_with_database.exception;

public class NotValidParameters extends RuntimeException {
    public NotValidParameters(String message) {
        super(message);
    }
}
