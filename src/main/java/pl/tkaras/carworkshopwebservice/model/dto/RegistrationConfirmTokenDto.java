package pl.tkaras.carworkshopwebservice.model.dto;

import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;

import java.time.LocalDateTime;

public class RegistrationConfirmTokenDto {

    private String token;
    private LocalDateTime createdOn;
    private LocalDateTime expiresOn;
    private LocalDateTime confirmedOn;

    public String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiredOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public LocalDateTime getConfirmedOn() {
        return confirmedOn;
    }

    public void setConfirmedOn(LocalDateTime confirmedOn) {
        this.confirmedOn = confirmedOn;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String token;
        private LocalDateTime createdOn;
        private LocalDateTime expiresOn;
        private LocalDateTime confirmedOn;

        public Builder token(String token){
            this.token = token;
            return this;
        }

        public Builder createdOn(LocalDateTime createdOn){
            this.createdOn = createdOn;
            return this;
        }

        public Builder expiresOn(LocalDateTime expiredOn){
            this.expiresOn = expiresOn;
            return this;
        }

        public Builder confirmedOn(LocalDateTime confirmedOn){
            this.confirmedOn = confirmedOn;
            return this;
        }

        public RegistrationConfirmTokenDto build(){
            RegistrationConfirmTokenDto tokenDto = new RegistrationConfirmTokenDto();
            tokenDto.token = token;
            tokenDto.createdOn = createdOn;
            tokenDto.expiresOn = expiresOn;
            tokenDto.confirmedOn = confirmedOn;
            return tokenDto;
        }




    }
}
