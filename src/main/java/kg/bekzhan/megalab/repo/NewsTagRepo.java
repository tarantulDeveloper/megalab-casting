package kg.bekzhan.megalab.repo;

import kg.bekzhan.megalab.entities.ENewsTags;
import kg.bekzhan.megalab.entities.NewsTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsTagRepo extends JpaRepository<NewsTag, Integer> {
    Optional<NewsTag> findNewsTagByTag(ENewsTags tag);
}
