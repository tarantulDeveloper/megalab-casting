package kg.bekzhan.megalab.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity(name = "posts")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "posts_tags",
    joinColumns = @JoinColumn(name = "post_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<NewsTag> tags = new HashSet<>();

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "author_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Comment> commentList = new ArrayList<>();


}
