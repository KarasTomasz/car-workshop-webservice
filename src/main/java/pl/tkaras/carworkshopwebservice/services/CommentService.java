package pl.tkaras.carworkshopwebservice.services;

import org.springframework.stereotype.Service;
import pl.tkaras.carworkshopwebservice.exceptions.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exceptions.CommentNotFoundException;
import pl.tkaras.carworkshopwebservice.models.dtos.CommentDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.CommentDtoMapper;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;
import pl.tkaras.carworkshopwebservice.repositories.CommentRepository;

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

    public Optional<CommentDto> getComment(Long id){
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        CommentDto commentDto = commentDtoMapper.mapToDto(comment);
        return Optional.ofNullable(commentDto);
    }

    public List<CommentDto> getCommentsByUsername(String username){
        if(commentRepo.existsByAppUsername(username)) {
            Long userId = commentRepo.findUserIdByUsername(username);
            return commentDtoMapper.mapToDtos(commentRepo.findAllByAppUserId(userId));
        }
        else{
            throw new AppUserNotFoundException(username);
        }
    }

    public List<CommentDto> getAllComments(){
        return commentDtoMapper.mapToDtos(commentRepo.findAll());
    }

    @Transactional
    public CommentDto addComment(String username, CommentDto commentdto){
        AppUser appUser = commentRepo.findUserByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        Comment comment = new Comment().builder()
                .description(commentdto.getDescription())
                .createdOn(LocalDateTime.now())
                .appUser(appUser)
                .build();

        Comment savedComment = commentRepo.save(comment);
        return commentDtoMapper.mapToDto(savedComment);
    }

    public CommentDto updateComment(Long id, CommentDto commentdto){

            Comment foundComment = commentRepo.findById(id)
                            .orElseThrow(() -> new CommentNotFoundException(id));

            foundComment.setDescription(commentdto.getDescription());
            foundComment.setUpdatedOn(LocalDateTime.now());

            return commentDtoMapper.mapToDto(commentRepo.save(foundComment));
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
