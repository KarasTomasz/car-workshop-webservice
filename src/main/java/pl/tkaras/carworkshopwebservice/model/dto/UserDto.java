package pl.tkaras.carworkshopwebservice.model.dto;

import pl.tkaras.carworkshopwebservice.model.entity.Rank;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;

public class UserDto {

    private String username;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRank() {
        return role;
    }

    public void setUserRank(String role) {
        this.role = role;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder{
        private String username;
        private String role;

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder userRank(String role){
            this.role = role;
            return this;
        }

        public UserDto build(){
            UserDto userDto = new UserDto();
            userDto.username = username;
            userDto.role = role;
            return userDto;
        }

    }
}
