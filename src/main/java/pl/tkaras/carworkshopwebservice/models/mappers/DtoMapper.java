package pl.tkaras.carworkshopwebservice.models.mappers;

import java.util.List;

public interface DtoMapper<T, K> {

    List<K> mapToDtos(List<T> list);
    K mapToDto(T t);

}
