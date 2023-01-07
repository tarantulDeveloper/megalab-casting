package kg.bekzhan.megalab.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_id_generator", sequenceName = "comments_sequence", allocationSize = 1)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String authorName;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Comment reply;

}
