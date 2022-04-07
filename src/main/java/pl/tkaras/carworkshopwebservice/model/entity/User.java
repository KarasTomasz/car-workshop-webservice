package pl.tkaras.carworkshopwebservice.model.entity;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String LastName;
    private String username;
    private String password;
    @Email
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Rank userRank;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Car> cars;

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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

    public Rank getUserRank() {
        return userRank;
    }

    public void setUserRank(Rank userRank) {
        this.userRank = userRank;
    }
}
