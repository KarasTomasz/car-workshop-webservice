package pl.tkaras.carworkshopwebservice.models.mappers.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.models.dtos.CommentDto;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;
import pl.tkaras.carworkshopwebservice.models.mappers.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper implements DtoMapper<Comment, CommentDto> {

    public CommentMapper(){ }

    @Override
    public List<CommentDto> mapToDtos(List<Comment> list) {
        return list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .description(comment.getDescription())
                .username(comment.getAppUser().getUsername())
                .createdOn(comment.getCreatedOn())
                .build();
    }

    @Override
    public List<Comment> mapToEntities(List<CommentDto> list) {
        return list.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Comment mapToEntity(CommentDto commentDto) {
        return Comment.builder()
                .description(commentDto.getDescription())
                .build();
    }
}
