package pl.tkaras.carworkshopwebservice.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistrationConfirmToken {

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
}
