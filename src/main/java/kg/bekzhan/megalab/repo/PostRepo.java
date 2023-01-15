package kg.bekzhan.megalab.repo;

import kg.bekzhan.megalab.entities.NewsTag;
import kg.bekzhan.megalab.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    @Modifying()
    @Query(value = "DELETE FROM posts where id = ?1", nativeQuery = true)
    @Transactional
    void deletePostByIdCustomMethod(Integer id);


    List<Post> findByTagsIn(List<NewsTag> tags);

}
