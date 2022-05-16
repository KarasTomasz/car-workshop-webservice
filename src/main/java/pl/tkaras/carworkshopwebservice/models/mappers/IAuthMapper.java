package pl.tkaras.carworkshopwebservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.tkaras.carworkshopwebservice.models.dtos.AuthDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAuthMapper {

    List<AuthDto> mapToDtos(List<AppUser> list);

    AuthDto mapToDto(AppUser user);

    List<AppUser> mapToEntities(List<AuthDto> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isEnabled", ignore = true)
    @Mapping(target = "isCredentialsNonExpired", ignore = true)
    @Mapping(target = "isAccountNonLocked", ignore = true)
    @Mapping(target = "isAccountNonExpired", ignore = true)
    @Mapping(target = "appUserAddresses", ignore = true)
    AppUser mapToEntity(AuthDto authDto);
}