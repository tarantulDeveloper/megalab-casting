package kg.bekzhan.megalab.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(generator = "post_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "post_id_generator", sequenceName = "posts_sequence", allocationSize = 1)
    private int id;
    private String header;
    @Column(columnDefinition = "TEXT")
    private String text;
    private String photoURL;
    @Temporal(TemporalType.DATE)
    private Date publishedDate;


}
