package pl.tkaras.carworkshopwebservice.repository;

import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);
    List<Comment> findAll();
    Comment save(Comment entity);
    void deleteById(Long id);
    boolean existsById(Long id);
}
