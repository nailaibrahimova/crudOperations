package az.pashabank.learning_sessions.crud_operations_with_database.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public CustomerEntity() {
    }

    public CustomerEntity(String name) {
        this.name = name;
    }
}

