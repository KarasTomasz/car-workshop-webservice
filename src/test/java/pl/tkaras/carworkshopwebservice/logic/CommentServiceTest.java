package pl.tkaras.carworkshopwebservice.logic;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tkaras.carworkshopwebservice.exception.AppUserNotFoundException;
import pl.tkaras.carworkshopwebservice.exception.CommentNotFoundException;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.model.mapper.impl.CommentDtoMapper;
import pl.tkaras.carworkshopwebservice.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private CommentDtoMapper commentDtoMapper;

    @BeforeEach
    void init(){
        commentDtoMapper = new CommentDtoMapper();
        commentService = new CommentService(commentRepository, commentDtoMapper);
    }

    @Test
    void shouldReturnAllCommentsIfExist(){
        //given
        when(commentRepository.findAll()).thenReturn(prepareCommentData());

        //when
        List<CommentDto> commentDtos = commentService.getAllComments();

        //then
        assertEquals(prepareCommentDtoData().size(), commentDtos.size());
    }

    @Test
    void shouldReturnEmptyListIfCommentsNotExist(){
        //given
        when(commentRepository.findAll()).thenReturn(new ArrayList<>());

        //when
        List<CommentDto> commentDtos = commentService.getAllComments();

        //then
        assertEquals(new ArrayList<>(), commentDtos);
    }

    @Test
    void shouldReturnAllCommentsByUsernameIfExist(){
        //given
        when(commentRepository.existsByAppUsername(anyString())).thenReturn(true);
        when(commentRepository.findAllByAppUserId(anyLong())).thenReturn(prepareCommentData());

        //when
        List<CommentDto> commentDtos = commentService.getCommentsByUsername(prepareComment_1().getAppUser().getUsername());

        //then
        assertEquals(prepareCommentDtoData().size(), commentDtos.size());
    }

    @Test
    void shouldThrowExceptionWhenAllCommentsByUsernameAndAppUserNotExist(){
        //given
        when(commentRepository.existsByAppUsername(anyString())).thenReturn(false);

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> commentService.getCommentsByUsername(prepareComment_1().getAppUser().getUsername()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void shouldReturnCommentByIdIfExist(){
        //given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(prepareComment_1()));

        //when
        Optional<CommentDto> commentDto = commentService.getComment(1L);

        //then
        assertEquals(prepareComment_1().getAppUser().getUsername(), commentDto.orElseThrow().getUsername());
    }

    @Test
    void shouldThrowExceptionWhenCommentByIdAndCommentNotExist(){
        //given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> commentService.getComment(1L));

        //then
        assertThat(exception)
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("not found");

    }

    @Test
    void shouldAddCommentIfAppUserExist(){
        //given
        when(commentRepository.findUserByUsername(anyString())).thenReturn(Optional.of(prepareAppUser_1()));
        when(commentRepository.save(any())).thenReturn(prepareComment_1());

        //when
        CommentDto commentDto = commentService.addComment(prepareComment_1().getAppUser().getUsername(), prepareCommentDto_1());

        //then
        assertEquals(prepareCommentDto_1().getUsername(), commentDto.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenAddCommentAndAppUserNotExist(){
        //given
        when(commentRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> commentService.addComment(prepareComment_1().getAppUser().getUsername(), prepareCommentDto_1()));

        //then
        assertThat(exception)
                .isInstanceOf(AppUserNotFoundException.class)
                        .hasMessageContaining("not found");
    }

    @Test
    void shouldUpdateCommentIfExist(){
        //given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(prepareComment_1()));
        when(commentRepository.save(any())).thenReturn(prepareComment_1());

        //when
        CommentDto commentDto = commentService.updateComment(1L, prepareCommentDto_1());

        //then
        assertEquals(prepareComment_1().getAppUser().getUsername(), commentDto.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUpdateCommentAndCommentNotExist(){
        //given
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        var exception = AssertionsForClassTypes.catchThrowable(() -> commentService.updateComment(1L, prepareCommentDto_1()));

        //then
        assertThat(exception)
                .isInstanceOf(CommentNotFoundException.class)
                        .hasMessageContaining("not found");
    }

    AppUser prepareAppUser_1(){
        return AppUser.builder()
                .username("client 1")
                .password("password")
                .email("client1@gmail.com")
                .firstname("Jan")
                .lastname("Kowalski")
                .street("Kosciuszki")
                .zipCode("50-555")
                .city("Wroclaw")
                .build();
    }

    List<Comment> prepareCommentData(){
        return Stream.of(prepareComment_1(), prepareComment_2())
                .collect(Collectors.toList());
    }

    Comment prepareComment_1(){
       return Comment.builder()
               .appUser(prepareAppUser_1())
               .description("description 1")
               .createdOn(LocalDateTime.now())
               .updatedOn(LocalDateTime.now())
               .build();
    }

    Comment prepareComment_2(){
        return Comment.builder()
                .appUser(prepareAppUser_1())
                .description("description 2")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    List<CommentDto> prepareCommentDtoData(){
        return Stream.of(prepareCommentDto_1(), prepareCommentDto_2())
                .collect(Collectors.toList());
    }

    CommentDto prepareCommentDto_1(){
        return CommentDto.builder()
                .username(prepareAppUser_1().getUsername())
                .description("description 1")
                .createdOn(LocalDateTime.now())
                .build();
    }

    CommentDto prepareCommentDto_2(){
        return CommentDto.builder()
                .username(prepareAppUser_1().getUsername())
                .description("description 2")
                .createdOn(LocalDateTime.now())
                .build();
    }

}