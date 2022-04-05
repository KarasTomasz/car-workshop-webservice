package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDtoMapper;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public CommentDto getSingleComment(Long id){
        Comment comment = commentRepo.findById(id).orElseThrow();
        CommentDto commentDto = CommentDtoMapper.mapToCommentDto(comment);
        return commentDto;
    }

    public List<CommentDto> getAllComments(){
        return CommentDtoMapper.mapToCommentDtos(commentRepo.findAll());
    }

    public Comment addComment(Comment entity){
        return commentRepo.save(entity);
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, Comment entity){
        if(commentRepo.existsById(id)){
            Comment comment = commentRepo.findById(id).orElseThrow();
            comment.setDescription(entity.getDescription());
            commentRepo.save(comment);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deleteComment(Long id){
        if(commentRepo.existsById(id)){
            commentRepo.deleteById(id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
