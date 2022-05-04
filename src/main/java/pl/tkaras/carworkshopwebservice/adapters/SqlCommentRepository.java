package pl.tkaras.carworkshopwebservice.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;
import pl.tkaras.carworkshopwebservice.repositories.CommentRepository;

@Repository
public interface SqlCommentRepository extends JpaRepository<Comment, Long>, CommentRepository {

}
