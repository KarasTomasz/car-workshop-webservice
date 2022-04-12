package pl.tkaras.carworkshopwebservice.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.CommentDtoMapper;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepo;
    private CommentDtoMapper commentDtoMapper;

    public CommentService(CommentRepository commentRepo, CommentDtoMapper commentDtoMapper) {
        this.commentRepo = commentRepo;
        this.commentDtoMapper = commentDtoMapper;
    }

    public CommentDto getSingleComment(Long id){
        if(commentRepo.existsById(id)){
            return commentDtoMapper.mapToDto(commentRepo.findById(id).get());
        }
        throw new UsernameNotFoundException(String.format("User with id %s not found", id));
    }

    public List<CommentDto> getAllComments(){
        return commentDtoMapper.mapToDtos(commentRepo.findAll());
    }

    public CommentDto addComment(Comment entity){
        return commentDtoMapper.mapToDto(commentRepo.save(entity));
    }

    public ResponseEntity updateComment(Long id, Comment entity){
        if(commentRepo.existsById(id)){
            Comment comment = commentRepo.findById(id).get();
            comment.setDescription(entity.getDescription());
            commentRepo.save(comment);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteComment(Long id){
        if(commentRepo.existsById(id)){
            commentRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
}
