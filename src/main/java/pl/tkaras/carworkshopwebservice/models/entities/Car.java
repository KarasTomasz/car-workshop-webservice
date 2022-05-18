package pl.tkaras.carworkshopwebservice.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mark;

    private String model;

    private BigDecimal price;

    private String imageUrl;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "appUserId")
    private AppUser appUser;

}
