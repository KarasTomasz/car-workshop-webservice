package pl.tkaras.carworkshopwebservice.models.mappers;

import org.mapstruct.Mapper;
import pl.tkaras.carworkshopwebservice.models.dtos.CommentDto;
import pl.tkaras.carworkshopwebservice.models.entities.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

    List<CommentDto> mapToDtos(List<Comment> list);

    CommentDto mapToDto(Comment comment);

    List<Comment> mapToEntities(List<CommentDto> list);

    Comment mapToEntity(CommentDto commentDto);
}