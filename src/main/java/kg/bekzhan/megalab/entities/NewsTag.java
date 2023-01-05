package kg.bekzhan.megalab.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class NewsTag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_tags_generator")
    @SequenceGenerator(
            name = "news_tags_generator",
            sequenceName = "tags_sequence",
            allocationSize = 1
    )
    private int id;

    @Enumerated(EnumType.STRING)
    private ENewsTags tag;

    public NewsTag(ENewsTags tag) {
        this.tag = tag;
    }
}
