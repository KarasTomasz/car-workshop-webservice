package pl.tkaras.carworkshopwebservice.services;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.CommentNotFoundException;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;
import pl.tkaras.carworkshopwebservice.repositories.CommentRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment getComment(Long id){
        return commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public List<Comment> getCommentsByUsername(String username){
        if(commentRepo.existsByAppUsername(username)) {
            Long userId = commentRepo.findUserIdByUsername(username);
            return commentRepo.findAllByAppUserId(userId);
        }
        else{
            throw new AppUserNotFoundException(username);
        }
    }

    public List<Comment> getAllComments(){
        return commentRepo.findAll();
    }

    @Transactional
    public Comment addComment(String username, Comment comment){
        AppUser appUser = commentRepo.findUserByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        comment.setAppUser(appUser);
        comment.setCreatedOn(LocalDateTime.now());

        return commentRepo.save(comment);
    }

    public Comment updateComment(Long id, Comment comment){

            Comment foundComment = commentRepo.findById(id)
                            .orElseThrow(() -> new CommentNotFoundException(id));

            foundComment.setDescription(comment.getDescription());
            foundComment.setUpdatedOn(LocalDateTime.now());

            return commentRepo.save(foundComment);
    }

    public void deleteComment(Long id){
        if(commentRepo.existsById(id)){
            commentRepo.deleteById(id);
        }
        else {
            throw new CommentNotFoundException(id);
        }
    }
}
