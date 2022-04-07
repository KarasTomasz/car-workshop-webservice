package pl.tkaras.carworkshopwebservice.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
public class Audit {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist(){
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preMarge(){
        this.updatedOn = LocalDateTime.now();
    }
}
