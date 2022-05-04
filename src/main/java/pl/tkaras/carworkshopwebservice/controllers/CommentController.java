package pl.tkaras.carworkshopwebservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.services.CommentService;
import pl.tkaras.carworkshopwebservice.models.dtos.CommentDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<CommentDto> getComment(@RequestParam("id") Long id){
        return commentService.getComment(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/all")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(@RequestParam String username){
        return new ResponseEntity<>(commentService.getCommentsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> addComment(@RequestParam("username") String username, @RequestBody CommentDto comment){
        return new ResponseEntity<>(commentService.addComment(username, comment), HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<CommentDto> updateComment(@RequestParam("id") Long id, @RequestBody CommentDto comment){
        return ResponseEntity.ok().body(commentService.updateComment(id, comment));
    }

    @DeleteMapping("")
    @PreAuthorize("hasAuthority('comment:delete')")
    public ResponseEntity<Object> deleteComment(@RequestParam("id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
