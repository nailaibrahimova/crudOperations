package az.pashabank.learning_sessions.crud_operations_with_database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrudOperationsWithDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudOperationsWithDatabaseApplication.class, args);
    }

}
