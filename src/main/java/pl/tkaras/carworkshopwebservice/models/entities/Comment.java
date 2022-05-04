package pl.tkaras.carworkshopwebservice.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    @ManyToOne
    @JoinColumn(name = "appUserId")
    private AppUser appUser;

    public Comment() {
    }

    public Long getId() { return id; }

    void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String description;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
        private AppUser appUser;

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder createdOn(LocalDateTime createdOn){
            this.createdOn = createdOn;
            return this;
        }

        public Builder updatedOn(LocalDateTime updatedOn){
            this.updatedOn = updatedOn;
            return this;
        }

        public Builder appUser(AppUser appUser){
            this.appUser = appUser;
            return this;
        }

        public Comment build(){
            Comment comment = new Comment();
            comment.setDescription(description);
            comment.setCreatedOn(createdOn);
            comment.setUpdatedOn(updatedOn);
            comment.setAppUser(appUser);
            return comment;
        }
    }
}
