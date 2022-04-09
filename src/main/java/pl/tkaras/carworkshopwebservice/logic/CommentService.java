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
        if(!commentRepo.existsById(id)){
            throw new UsernameNotFoundException("User with given id does not exists");
        }
        return commentDtoMapper.mapToDto(commentRepo.findById(id).get());
    }

    public List<CommentDto> getAllComments(){
        return commentDtoMapper.mapToDtos(commentRepo.findAll());
    }

    public CommentDto addComment(Comment entity){
        return commentDtoMapper.mapToDto(commentRepo.save(entity));
    }

    //@Transactional
    public ResponseEntity<?> updateComment(Long id, Comment entity){
        if(commentRepo.existsById(id)){
            Comment comment = commentRepo.findById(id).get();
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
