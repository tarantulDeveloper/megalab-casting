package kg.bekzhan.megalab.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nickName;
    private String password;
    private String photoPath;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String lastName, String firstName, String nickName, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.nickName = nickName;
        this.password = password;
    }
}
