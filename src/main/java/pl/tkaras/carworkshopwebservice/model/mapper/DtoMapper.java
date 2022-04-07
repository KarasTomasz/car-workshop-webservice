package pl.tkaras.carworkshopwebservice.model.mapper;

import pl.tkaras.carworkshopwebservice.model.dto.CarDto;
import pl.tkaras.carworkshopwebservice.model.entity.Car;

import java.util.List;

public interface DtoMapper<T, K> {

    List<K> mapToDtos(List<T> list);
    K mapToDto(T t);

}
