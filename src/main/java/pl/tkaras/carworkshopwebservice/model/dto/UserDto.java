package pl.tkaras.carworkshopwebservice.model.dto;

import pl.tkaras.carworkshopwebservice.model.entity.Rank;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;

public class UserDto {

    private String username;
    private Rank userRank;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rank getUserRank() {
        return userRank;
    }

    public void setUserRank(Rank userRank) {
        this.userRank = userRank;
    }

    static Builder builder(){
        return new Builder();
    }

    static class Builder{
        private String username;
        private Rank userRank;

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder userRank(Rank userRank){
            this.userRank = userRank;
            return this;
        }

        public UserDto build(){
            UserDto userDto = new UserDto();
            userDto.username = username;
            userDto.userRank = userRank;
            return userDto;
        }

    }
}
