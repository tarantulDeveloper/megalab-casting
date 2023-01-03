package kg.bekzhan.megalab.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_generator")
    @SequenceGenerator(
            name = "roles_generator",
            sequenceName = "roles_sequence",
            allocationSize = 1
    )
    private int id;
    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role (ERole name) {
        this.name = name;
    }
}
