package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.CommentDtoMapper;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.repository.CommentRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final CommentDtoMapper commentDtoMapper;

    public CommentService(CommentRepository commentRepo, CommentDtoMapper commentDtoMapper) {
        this.commentRepo = commentRepo;
        this.commentDtoMapper = commentDtoMapper;
    }

    public Optional<CommentDto> getCommentById(Long id){
        if (commentRepo.existsById(id)){
            Optional<Comment> comment = commentRepo.findById(id);
            CommentDto commentDto = commentDtoMapper.mapToDto(comment.get());
            return Optional.ofNullable(commentDto);
        }
        else {
            throw new IllegalStateException(String.format("Comment with id %s not found", id));
        }
    }

    public Optional<CommentDto> getCommentByNum(String username, Integer num){

        if(num == 0){ throw new IllegalStateException(String.format("Num %s is illegal value", num));}

        if (commentRepo.existsByAppUsername(username)) {
            Long userId = commentRepo.findUserIdByUsername(username);
            List<Comment> comments = commentRepo.findAllByAppUserId(userId);
            long cnt = comments.size();
            if (cnt > 0 && cnt >= num) {
                CommentDto commentDto = commentDtoMapper.mapToDto(comments.get(num - 1));
                return Optional.ofNullable(commentDto);
            }
            else
                throw new IllegalStateException(String.format("Comment with num %s not found", num));
        }
        else {
            throw new IllegalStateException(String.format("%s not found", username));
        }
    }

    public List<CommentDto> getAllComments(String username){
        if(commentRepo.existsByAppUsername(username)) {
            Long userId = commentRepo.findUserIdByUsername(username);
            return commentDtoMapper.mapToDtos(commentRepo.findAllByAppUserId(userId));
        }
        else{
            throw new IllegalStateException(String.format("%s not found", username));
        }
    }

    @Transactional
    public CommentDto addComment(String username, Comment comment){
        AppUser appUser = commentRepo.findUserByUsername(username);
        if(appUser == null) throw new IllegalStateException(String.format("%s not found", username));
        comment.setAppUser(appUser);
        comment.setCreatedOn(LocalDateTime.now());
        Comment savedComment = commentRepo.save(comment);
        return commentDtoMapper.mapToDto(savedComment);
    }

    public CommentDto updateCommentById(Long id, Comment comment){
        if(commentRepo.existsById(id)){
            comment.setUpdatedOn(LocalDateTime.now());
            Comment returnedComment = commentRepo.save(comment);
            return commentDtoMapper.mapToDto(returnedComment);
        }
        else {
            throw new IllegalStateException(String.format("Id %s not found", id));
        }
    }

    public CommentDto updateCommentByCreatedOn(Comment comment){
        if(commentRepo.existsByCreatedOn(comment.getCreatedOn())) {
            Comment foundComment = commentRepo.findByCreatedOn(comment.getCreatedOn());
            foundComment.setDescription(comment.getDescription());
            foundComment.setUpdatedOn(LocalDateTime.now());
            commentRepo.save(foundComment);
            return commentDtoMapper.mapToDto(foundComment);
        }
        else {
            throw new IllegalStateException("Comment not found");
        }
    }

    public void deleteCommentById(Long id){
        if(commentRepo.existsById(id)){
            commentRepo.deleteById(id);
        }
        else {
            throw new IllegalStateException(String.format("Comment with id %s not found", id));
        }
    }

    public void deleteCommentByCreatedOn(LocalDateTime createdOn){
        if(commentRepo.existsByCreatedOn(createdOn)){
            commentRepo.deleteByCreatedOn(createdOn);
        }
        else {
            throw new IllegalStateException("Comment not found");
        }
    }
}
