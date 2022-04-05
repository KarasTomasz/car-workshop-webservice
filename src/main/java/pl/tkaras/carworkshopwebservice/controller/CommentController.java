package pl.tkaras.carworkshopwebservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tkaras.carworkshopwebservice.logic.CommentService;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    private CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public List<CommentDto> getComments(){
        return commentService.getAllComments();
    }

    @PostMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id){
        return commentService.getSingleComment(id);
    }

    @PutMapping("")
    public Comment addComment(@RequestBody Comment entity){
        return  commentService.addComment(entity);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody Comment comment){
        return commentService.updateComment(id, comment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }

}
