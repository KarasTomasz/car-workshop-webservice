package pl.tkaras.carworkshopwebservice.models.dtos;

import io.swagger.annotations.ApiModelProperty;

public class CarDto {

    @ApiModelProperty(readOnly = true)
    private Long id;
    @ApiModelProperty(required = true)
    private String username;
    @ApiModelProperty(required = true)
    private String mark;
    @ApiModelProperty(required = true)
    private String model;
    private String description;

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
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

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder{

        private Long id;
        private String username;
        private String mark;
        private String model;
        private String description;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

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

        public CarDto build(){
            CarDto carDto = new CarDto();
            carDto.id = id;
            carDto.description = description;
            carDto.mark = mark;
            carDto.model = model;
            carDto.username = username;
            return carDto;
        }
    }
}
