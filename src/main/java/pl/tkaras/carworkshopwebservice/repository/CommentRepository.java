package pl.tkaras.carworkshopwebservice.repository;

import org.springframework.data.jpa.repository.Query;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    boolean existsById(Long id);

    @Query("SELECT CASE WHEN (COUNT(au) > 0) THEN true ELSE false end " +
            "FROM AppUser au where au.username = ?1" )
    boolean existsByAppUsername(String username);
    Optional<Comment> findById(Long id);

    @Query("SELECT au.id FROM AppUser au WHERE au.username = ?1")
    Long findUserIdByUsername(String username);

    @Query("SELECT au FROM AppUser au WHERE au.username = ?1")
    Optional<AppUser> findUserByUsername(String username);

    List<Comment> findAll();
    List<Comment> findAllByAppUserId(Long id);
    Comment save(Comment comment);
    void deleteById(Long id);
}
