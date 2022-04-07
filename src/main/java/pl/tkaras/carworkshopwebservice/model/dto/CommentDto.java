package pl.tkaras.carworkshopwebservice.model.dto;

import pl.tkaras.carworkshopwebservice.model.entity.Comment;
import pl.tkaras.carworkshopwebservice.model.entity.User;

import java.time.LocalDateTime;

public class CommentDto {

    private String description;
    private Long userId;

    private CommentDto(){
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    void setUserId(Long userId) {
        this.userId = userId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String description;
        private Long userId;

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder userId(Long userId){
            this.userId = userId;
            return this;
        }

        public CommentDto build(){
            CommentDto commentDto = new CommentDto();
            commentDto.description = description;
            commentDto.userId = userId;
            return commentDto;
        }
    }
}
