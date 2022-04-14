package pl.tkaras.carworkshopwebservice.model.dto;

import java.time.LocalDateTime;

public class CommentDto {

    private String description;
    private String username;
    private LocalDateTime createdOn;

    private CommentDto(){
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String userName) {
        this.username = userName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String description;
        private String username;
        private LocalDateTime createdOn;

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder createdOn(LocalDateTime createdOn){
            this.createdOn = createdOn;
            return this;
        }

        public CommentDto build(){
            CommentDto commentDto = new CommentDto();
            commentDto.description = description;
            commentDto.username = username;
            commentDto.createdOn = createdOn;
            return commentDto;
        }
    }
}
