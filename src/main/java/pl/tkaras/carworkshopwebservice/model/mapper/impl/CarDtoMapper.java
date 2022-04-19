package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.CarDto;
import pl.tkaras.carworkshopwebservice.model.entity.Car;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarDtoMapper implements DtoMapper<Car, CarDto> {

    public CarDtoMapper(){ }

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
}
