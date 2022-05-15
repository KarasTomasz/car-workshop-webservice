package pl.tkaras.carworkshopwebservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT CASE WHEN (COUNT(au) > 0) THEN true ELSE false end " +
            "FROM AppUser au where au.username = ?1" )
    boolean existsByAppUsername(String username);

    @Query("SELECT au.id FROM AppUser au WHERE au.username = ?1")
    Long findUserIdByUsername(String username);

    @Query("SELECT au FROM AppUser au WHERE au.username = ?1")
    Optional<AppUser> findUserByUsername(String username);

    List<Comment> findAllByAppUserId(Long id);

}
