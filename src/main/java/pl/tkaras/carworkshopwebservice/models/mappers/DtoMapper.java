package pl.tkaras.carworkshopwebservice.models.mappers;

import java.util.List;

public interface DtoMapper<T, DTO> {

    List<DTO> mapToDtos(List<T> list);

    DTO mapToDto(T t);

    List<T> mapToEntities(List<DTO> list);

    T mapToEntity(DTO dto);

}
