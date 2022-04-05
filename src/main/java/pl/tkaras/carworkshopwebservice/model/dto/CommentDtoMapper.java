package pl.tkaras.carworkshopwebservice.model.dto;

import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentDtoMapper {

    private CommentDtoMapper(){ }

    public static List<CommentDto> mapToCommentDtos(List<Comment> comments){
        return comments.stream()
                .map(comment -> mapToCommentDto(comment))
                .collect(Collectors.toList());
    }

    public static CommentDto mapToCommentDto(Comment comment){
        return CommentDto.builder()
                .description(comment.getDescription())
                .userId(comment.getUserId())
                .build();
    }
}
