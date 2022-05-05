package pl.tkaras.carworkshopwebservice.models.mappers.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.models.dtos.CarDto;
import pl.tkaras.carworkshopwebservice.models.entities.Car;
import pl.tkaras.carworkshopwebservice.models.mappers.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper implements DtoMapper<Car, CarDto> {

    public CarMapper(){ }

    @Override
    public List<CarDto> mapToDtos(List<Car> list) {
        return list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarDto mapToDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .description(car.getDescription())
                .mark(car.getMark())
                .model(car.getModel())
                .username(car.getAppUser().getUsername())
                .build();
    }

    @Override
    public List<Car> mapToEntities(List<CarDto> list) {
        return list.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Car mapToEntity(CarDto carDto) {
        return Car.builder()
                .description(carDto.getDescription())
                .mark(carDto.getMark())
                .model(carDto.getModel())
                .build();
    }
}
