package pl.tkaras.carworkshopwebservice.models.mappers;

import org.mapstruct.Mapper;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserAddressDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserAddress;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAppUserAddressMapper {

    List<AppUserAddressDto> mapToDtos(List<AppUserAddress> list);

    AppUserAddressDto mapToDto(AppUserAddress userAddress);

    List<AppUserAddress> mapToEntities(List<AppUserAddressDto> list);

    AppUserAddress mapToEntity(AppUserAddressDto userAddressDto);
}