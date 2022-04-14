package pl.tkaras.carworkshopwebservice.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.tkaras.carworkshopwebservice.logic.CommentService;
import pl.tkaras.carworkshopwebservice.model.dto.CommentDto;
import pl.tkaras.carworkshopwebservice.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Mock
    Comment comment;

    @Mock
    CommentDto commentDto;

    @Mock
    CommentService commentService;

    @Mock
    private CommentController commentController;

    @BeforeEach
    public void init() {
        commentController = new CommentController(commentService);
        given(commentService.getAllComments(prepareComment1().getUsername())).willReturn(prepareCommentData());
    }

    @AfterEach
    public void clear() {
        commentService.getAllComments(prepareComment1().getUsername()).clear();
    }

    @Test
    @DisplayName("Should return commentDto which is equal to commentDto1 and getCommentById is used")
    void httpGet_returnCommentWhenGetCommentById_test() {
        //given
        CommentDto commentDto1 = prepareComment1();
        given(commentService.getCommentById(anyLong())).willReturn(Optional.of(commentDto1));

        //when + then
        assertEquals(commentDto1, commentController.getCommentById(anyLong()).getBody());
    }

    @Test
    @DisplayName("Should return Status_Not_Found when comment not exists and getCommentById is used")
    void httpGet_returnNotFoundWhenGetCommentById_test() {
        //given
        given(commentService.getCommentById(anyLong())).willReturn(Optional.empty());

        //when + then:
        assertEquals(HttpStatus.NOT_FOUND, commentController.getCommentById(anyLong()).getStatusCode());
    }

    @Test
    @DisplayName("Should return commentDto which is equal to commentDto1 and getCommentByNum is used")
    void httpGet_returnCommentWhenGetCommentByNum_test() {
        //given
        CommentDto commentDto1 = prepareComment1();
        given(commentService.getCommentByNum(prepareComment1().getUsername(), 1)).willReturn(Optional.of(commentDto1));

        //when + then
        assertEquals(commentDto1, commentController.getCommentByNum(prepareComment1().getUsername(), 1).getBody());
    }

    @Test
    @DisplayName("Should return Status_Not_Found when comment not exists and getCommentByNum is used")
    void httpGet_returnNotFoundWhenGetCommentByNum_test() {
        //given
        given(commentService.getCommentByNum(prepareComment1().getUsername(), 1)).willReturn(Optional.empty());

        //when + then:
        assertEquals(HttpStatus.NOT_FOUND, commentController.getCommentByNum(prepareComment1().getUsername(), 1).getStatusCode());
    }

    @Test
    @DisplayName("Should return all commentDtos when getAllComments method is used")
    void httpGet_returnCommentDtoWhenGetAllComments_test() {
        //given
        when(commentService.getAllComments(prepareComment1().getUsername())).thenReturn(prepareCommentData());

        //when + then
        assertEquals(HttpStatus.OK, commentController.getComments(prepareComment1().getUsername()).getStatusCode());
    }

    @Test
    @DisplayName("Should return empty List and Status_Ok when comments not exist and getAllComments method is used")
    void httpGet_returnStatusWhenGetAllComments_test() {
        //given
        when(commentService.getAllComments(prepareComment1().getUsername())).thenReturn(new ArrayList<>());

        //when + then
        assertEquals(HttpStatus.OK, commentController.getComments(prepareComment1().getUsername()).getStatusCode());
        assertEquals(new ArrayList<>(), commentController.getComments(prepareComment1().getUsername()).getBody());
    }

    @Test
    @DisplayName("Should return Status_OK when added new comment")
    void httpPost_returnOkWhenAddComment_test() {
        //given:
        when(commentService.addComment(prepareComment1().getUsername(), comment)).thenReturn(commentDto);

        //when + then:
        assertEquals(HttpStatus.OK, commentController.addComment(prepareComment1().getUsername(), comment).getStatusCode());
    }

    @Test
    @DisplayName("Should return Exception when add new comment and user not found")
    void httpPost_returnExceptionWhenAddComment_test() {
        //given:
        when(commentService.addComment(prepareComment1().getUsername(), comment)).thenThrow(new IllegalStateException());

        //when + then:
        var exception = catchThrowable(() -> commentController.addComment(prepareComment1().getUsername(), comment));
        assertThat(exception).isInstanceOf(IllegalStateException.class);

    }

    @Test
    @DisplayName("Should return commentDto and Status_Ok when comment updateById")
    void httpPut_returnCommentDtoWhenUpdateCommentById_test() {
        //given
        CommentDto commentDto1 = prepareComment1();
        when(commentService.updateCommentById(1L ,comment)).thenReturn(commentDto1);

        //when + then
        assertEquals(commentDto1, commentController.updateCommentById(1L,comment).getBody());
        assertEquals(ResponseEntity.ok().build().getStatusCode(), commentController.updateCommentById(1L,comment).getStatusCode());
    }

    @Test
    @DisplayName("Should return Exception comment not exist and updateById")
    void httpPut_returnExceptionWhenUpdateCommentById_test() {
        //given
        when(commentService.updateCommentById(1L ,comment)).thenThrow(new IllegalStateException());

        //when + then
        var exception = catchThrowable(() -> commentController.updateCommentById(1L, comment));
        assertThat(exception).isInstanceOf(IllegalStateException.class);

    }

    @Test
    @DisplayName("Should return commentDto and Status_Ok when comment updateByCreatedOn")
    void httpPut_returnCommentDtoWhenUpdateCommentByCreatedOn_test() {
        //given
        CommentDto commentDto1 = prepareComment1();
        when(commentService.updateCommentByCreatedOn(comment)).thenReturn(commentDto1);

        //when + then
        assertEquals(commentDto1, commentController.updateCommentByCreatedOn(comment).getBody());
        assertEquals(ResponseEntity.ok().build().getStatusCode(), commentController.updateCommentByCreatedOn(comment).getStatusCode());
    }

    @Test
    @DisplayName("Should return Exception comment not exist and updateByCreatedOn")
    void httpPut_returnExceptionWhenUpdateCommentByCreatedOn_test() {
        //given
        when(commentService.updateCommentByCreatedOn(comment)).thenThrow(new IllegalStateException());

        //when + then
        var exception = catchThrowable(() -> commentController.updateCommentByCreatedOn(comment));
        assertThat(exception).isInstanceOf(IllegalStateException.class);

    }

    private List<CommentDto> prepareCommentData() {
        return Stream.of(prepareComment1(), prepareComment2(), prepareComment3())
                .collect(toList());
    }

    private CommentDto prepareComment1() {
        return CommentDto.builder()
                .username("AB")
                .description("comment_1_1")
                .createdOn(LocalDateTime.now().minusMinutes(3))
                .build();
    }

    private CommentDto prepareComment2() {
        return CommentDto.builder()
                .username("CD")
                .description("comment_1_2")
                .createdOn(LocalDateTime.now().minusMinutes(2))
                .build();
    }

    private CommentDto prepareComment3() {
        return CommentDto.builder()
                .username("EF")
                .description("comment_2_1")
                .createdOn(LocalDateTime.now().minusMinutes(1))
                .build();
    }

}