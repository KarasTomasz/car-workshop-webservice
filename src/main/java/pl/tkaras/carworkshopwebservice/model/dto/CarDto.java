package pl.tkaras.carworkshopwebservice.model.dto;

public class CarDto {

    private String mark;
    private String model;
    private String description;


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

        private String mark;
        private String model;
        private String description;

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
            carDto.description = description;
            carDto.mark = mark;
            carDto.model = model;
            return carDto;
        }

    }

}
