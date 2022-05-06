package pl.tkaras.carworkshopwebservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;
import pl.tkaras.carworkshopwebservice.models.mappers.impl.CommentMapper;
import pl.tkaras.carworkshopwebservice.services.CommentService;
import pl.tkaras.carworkshopwebservice.models.dtos.CommentDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<CommentDto> getComment(@RequestParam("id") Long id){
        CommentDto commentDto = commentMapper.mapToDto(commentService.getComment(id));
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @GetMapping("/username/all")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(@RequestParam String username){
        List<Comment> comments = commentService.getCommentsByUsername(username);
        List<CommentDto> commentDtos = commentMapper.mapToDtos(comments);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtos = commentMapper.mapToDtos(commentService.getAllComments());
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> addComment(@RequestParam("username") String username, @RequestBody CommentDto commentDto){
        Comment receivedComment= commentMapper.mapToEntity(commentDto);
        Comment commentToSend = commentService.addComment(username, receivedComment);
        return new ResponseEntity<>(commentMapper.mapToDto(commentToSend), HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> updateComment(@RequestParam("id") Long id, @RequestBody CommentDto commentDto){
        Comment receivedComment= commentMapper.mapToEntity(commentDto);
        Comment commentToSend = commentService.updateComment(id, receivedComment);
        return new ResponseEntity<>(commentMapper.mapToDto(commentToSend), HttpStatus.OK);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('comment:delete')")
    public ResponseEntity<?> deleteComment(@RequestParam("id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

}
