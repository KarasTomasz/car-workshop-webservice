package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CommentService;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public List<CommentDto> getComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    public CommentDto getComment(@PathVariable Long id){
        return commentService.getSingleComment(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('comment:write')")
    public CommentDto addComment(@RequestBody Comment entity){
        return  commentService.addComment(entity);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody Comment comment){
        return commentService.updateComment(id, comment);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:delete')")
    public ResponseEntity deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }

}
