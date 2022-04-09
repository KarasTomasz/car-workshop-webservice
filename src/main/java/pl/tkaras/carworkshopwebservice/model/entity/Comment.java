package pl.tkaras.carworkshopwebservice.model.entity;

import pl.tkaras.carworkshopwebservice.model.Audit;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    @Embedded
    private Audit audit = new Audit();

    private Comment() {
    }

    long getId() { return id; }

    void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
