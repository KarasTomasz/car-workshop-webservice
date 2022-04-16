package pl.tkaras.carworkshopwebservice.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistrationConfirmToken {

    public RegistrationConfirmToken(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @Column(nullable = false)
    private LocalDateTime expiresOn;
    private LocalDateTime confirmedOn;

    @ManyToOne
    @JoinColumn(nullable = false, name = "appUserId")
    private AppUser appUser;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getExpiredOn() {
        return expiresOn;
    }

    void setExpiredOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public LocalDateTime getConfirmedOn() {
        return confirmedOn;
    }

    public void setConfirmedOn(LocalDateTime confirmedOn) {
        this.confirmedOn = confirmedOn;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String token;
        private LocalDateTime createdOn;
        private LocalDateTime expiresOn;
        private AppUser appUser;

        public Builder token(String token){
            this.token = token;
            return this;
        }

        public Builder createdOn(LocalDateTime createdOn){
            this.createdOn = createdOn;
            return this;
        }
        public Builder expiresOn(LocalDateTime expiresOn){
            this.expiresOn = expiresOn;
            return this;
        }
        public Builder appUser(AppUser appUser){
            this.appUser = appUser;
            return this;
        }

        public RegistrationConfirmToken build(){
            RegistrationConfirmToken confirmToken = new RegistrationConfirmToken();
            confirmToken.token = token;
            confirmToken.createdOn = createdOn;
            confirmToken.expiresOn = expiresOn;
            confirmToken.appUser = appUser;
            return confirmToken;
        }

    }

}
