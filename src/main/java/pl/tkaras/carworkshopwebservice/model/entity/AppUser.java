package pl.tkaras.carworkshopwebservice.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(nullable = false)
    private String email;
    private String firstname;
    private String lastname;
    private String street;
    private String zipCode;
    private String city;

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String username;
        private String password;
        private String email;
        private String firstname;
        private String lastname;
        private String street;
        private String zipCode;
        private String city;

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }

        public Builder street(String street){
            this.street = street;
            return this;
        }

        public Builder zipCode(String zipCode){
            this.zipCode = zipCode;
            return this;
        }

        public Builder city(String city){
            this.city = city;
            return this;
        }

        public AppUser build(){
            AppUser user = new AppUser();
            user.username = username;
            user.password = password;
            user.email = email;
            user.firstname = firstname;
            user.lastname =lastname;
            user.street = street;
            user.zipCode = zipCode;
            user.city = city;
            return user;
        }
    }
}
