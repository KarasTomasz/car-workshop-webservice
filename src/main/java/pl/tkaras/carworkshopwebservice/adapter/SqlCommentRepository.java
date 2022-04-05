package pl.tkaras.carworkshopwebservice.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.repository.CommentRepository;

@Repository
public interface SqlCommentRepository extends JpaRepository<Comment, Long>, CommentRepository {

}
