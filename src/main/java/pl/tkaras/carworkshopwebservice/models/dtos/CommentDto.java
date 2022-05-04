package pl.tkaras.carworkshopwebservice.models.dtos;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class CommentDto {

    @ApiModelProperty(readOnly = true)
    private Long id;
    @ApiModelProperty(required = true)
    private String description;
    @ApiModelProperty(readOnly = true, required = true)
    private String username;
    @ApiModelProperty(readOnly = true)
    private LocalDateTime createdOn;

    private CommentDto(){
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
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

        private Long id;
        private String description;
        private String username;
        private LocalDateTime createdOn;

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder id(Long id){
            this.id = id;
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
            commentDto.id = id;
            commentDto.description = description;
            commentDto.username = username;
            commentDto.createdOn = createdOn;
            return commentDto;
        }
    }
}
