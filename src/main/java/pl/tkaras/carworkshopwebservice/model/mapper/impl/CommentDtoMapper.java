package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentDtoMapper implements DtoMapper<Comment, CommentDto> {

    public CommentDtoMapper(){ }

    @Override
    public List<CommentDto> mapToDtos(List<Comment> list) {
        return list.stream()
                .map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .description(comment.getDescription())
                .username(comment.getAppUser().getUsername())
                .createdOn(comment.getCreatedOn())
                .build();
    }
}
