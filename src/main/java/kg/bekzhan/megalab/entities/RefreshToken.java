package kg.bekzhan.megalab.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity(name = "refreshtoken")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue
    private int id;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private ZonedDateTime expiryDate;

}
