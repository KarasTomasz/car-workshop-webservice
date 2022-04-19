package pl.tkaras.carworkshopwebservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mark;
    private String model;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "appUserId")
    private AppUser appUser;


    public Car() {
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String mark;
        private String model;
        private String description;
        private LocalDateTime createdOn;
        private LocalDateTime updatedOn;
        private AppUser appUser;


        public Builder mark(String mark){
            this.mark = mark;
            return this;
        }

        public Builder model(String model){
            this.model = model;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder createdOn(LocalDateTime createdOn){
            this.createdOn = createdOn;
            return this;
        }

        public Builder updatedOn(LocalDateTime updatedOn){
            this.updatedOn = updatedOn;
            return this;
        }

        public Builder appUser(AppUser appUser){
            this.appUser = appUser;
            return this;
        }

        public Car build(){
            Car car = new Car();
            car.mark = mark;
            car.model = model;
            car.description = description;
            car.createdOn =createdOn;
            car.updatedOn = updatedOn;
            car.appUser = appUser;
            return car;
        }
    }
}
