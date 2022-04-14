package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CommentService;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<CommentDto> getCommentById( @PathVariable Long id){
        return commentService.getCommentById(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<CommentDto> getCommentByNum(@RequestParam String username, @RequestParam Integer num){
        return commentService.getCommentByNum(username, num)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //@PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity getComments(@RequestParam String username){
        return new ResponseEntity<>(commentService.getAllComments(username), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> addComment(@RequestParam("username") String username, @RequestBody Comment comment){
        return new ResponseEntity<>(commentService.addComment(username, comment), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable Long id, @RequestBody Comment comment){
        return ResponseEntity.ok().body(commentService.updateCommentById(id, comment));
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> updateCommentByCreatedOn(@RequestBody Comment comment){
        return ResponseEntity.ok().body(commentService.updateCommentByCreatedOn(comment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:delete')")
    public ResponseEntity deleteCommentById(@PathVariable Long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('comment:delete')")
    public ResponseEntity deleteCommentByCreatedOn(@RequestParam LocalDateTime createdOn){
        commentService.deleteCommentByCreatedOn(createdOn);
        return ResponseEntity.ok().build();
    }

}
