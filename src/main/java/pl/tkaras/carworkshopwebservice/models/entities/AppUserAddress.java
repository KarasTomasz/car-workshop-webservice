package pl.tkaras.carworkshopwebservice.models.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUserAddress {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String street;

    private String zipCode;

    private String city;

    @ManyToOne
    @JoinColumn(name = "appUserId")
    AppUser appUser;
}
