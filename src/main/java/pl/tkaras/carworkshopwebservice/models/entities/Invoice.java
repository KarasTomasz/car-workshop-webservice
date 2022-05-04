package pl.tkaras.carworkshopwebservice.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime paymentDeadline;
    private BigDecimal totalCost;
    private String description;
    @ManyToOne
    @JoinColumn(nullable = false, name = "appUserId")
    private AppUser appUser;
    @ManyToOne
    @JoinColumn(nullable = false, name = "carId")
    private Car car;


}
