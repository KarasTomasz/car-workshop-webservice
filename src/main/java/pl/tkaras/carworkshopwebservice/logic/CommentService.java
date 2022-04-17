package pl.tkaras.carworkshopwebservice.logic;

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
    public CommentDto addComment(String username, CommentDto commentdto){
        AppUser appUser = commentRepo.findUserByUsername(username);
        if(appUser == null) throw new IllegalStateException(String.format("%s not found", username));
        Comment comment = new Comment().builder()
                .description(commentdto.getDescription())
                .createdOn(LocalDateTime.now())
                .appUser(appUser)
                .build();
        Comment savedComment = commentRepo.save(comment);
        return commentDtoMapper.mapToDto(savedComment);
    }

    @Transactional
    public CommentDto updateCommentById(Long id, CommentDto commentdto){
        if(commentRepo.existsById(id)){
            Comment foundComment = commentRepo.findByCreatedOn(commentdto.getCreatedOn());
            foundComment.setDescription(commentdto.getDescription());
            foundComment.setUpdatedOn(LocalDateTime.now());
            return commentDtoMapper.mapToDto(commentRepo.save(foundComment));
        }
        else {
            throw new IllegalStateException(String.format("Id %s not found", id));
        }
    }

    public CommentDto updateCommentByCreatedOn(CommentDto commentdto){
        if(commentRepo.existsByCreatedOn(commentdto.getCreatedOn())) {
            Comment foundComment = commentRepo.findByCreatedOn(commentdto.getCreatedOn());
            foundComment.setDescription(commentdto.getDescription());
            foundComment.setUpdatedOn(LocalDateTime.now());
            return commentDtoMapper.mapToDto(commentRepo.save(foundComment));
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
