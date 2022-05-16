package pl.tkaras.carworkshopwebservice.models.mappers;

import org.mapstruct.Mapper;
import pl.tkaras.carworkshopwebservice.models.dtos.CarDto;
import pl.tkaras.carworkshopwebservice.models.entities.Car;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICarMapper {

    List<CarDto> mapToDtos(List<Car> list);

    CarDto mapToDto(Car car);

    List<Car> mapToEntities(List<CarDto> list);

    Car mapToEntity(CarDto carDto);
}