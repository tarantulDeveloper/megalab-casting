package kg.bekzhan.megalab.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_id_generator", sequenceName = "comments_sequence", allocationSize = 1)
    private int id;

}
