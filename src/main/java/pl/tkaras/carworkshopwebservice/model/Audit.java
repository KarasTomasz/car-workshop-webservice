package pl.tkaras.carworkshopwebservice.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
public class Audit {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void preMarge(){
        this.updatedAt = LocalDateTime.now();
    }
}
