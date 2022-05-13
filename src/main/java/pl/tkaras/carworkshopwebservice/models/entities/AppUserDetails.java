package pl.tkaras.carworkshopwebservice.models.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role = "CLIENT";

    private boolean isAccountNonExpired = false;

    private boolean isAccountNonLocked = false;

    private boolean isCredentialsNonExpired = false;

    private boolean isEnabled = false;

    @OneToOne
    @JoinColumn(name = "appUserId")
    AppUser appUser;
}
