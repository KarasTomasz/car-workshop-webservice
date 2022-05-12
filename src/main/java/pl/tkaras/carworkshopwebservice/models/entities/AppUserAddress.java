package pl.tkaras.carworkshopwebservice.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUserAddress {

    private String street;

    private String zipCode;

    private String city;

    @OneToOne
    @JoinColumn(name = "appUserId")
    AppUser appUser;
}
