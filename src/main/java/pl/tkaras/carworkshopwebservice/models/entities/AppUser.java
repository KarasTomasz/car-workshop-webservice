package pl.tkaras.carworkshopwebservice.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private String role = "CLIENT";

    private boolean isAccountNonExpired = false;

    private boolean isAccountNonLocked = false;

    private boolean isCredentialsNonExpired = false;

    private boolean isEnabled = false;

    @OneToMany
    @JoinColumn(name = "appUserAddressId")
    Set<AppUserAddress> appUserAddresses;

}
