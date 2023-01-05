package kg.bekzhan.megalab.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_sequence", allocationSize = 1)
    private int id;
    private String lastName;
    private String firstName;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String photoPath;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles = new HashSet<>();




    public User(String lastName, String firstName, String username, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }
}
