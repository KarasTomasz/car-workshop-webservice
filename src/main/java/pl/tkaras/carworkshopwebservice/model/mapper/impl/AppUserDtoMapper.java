package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserDtoMapper implements DtoMapper<AppUser, AppUserDto> {

    public AppUserDtoMapper(){ }

    @Override
    public List<AppUserDto> mapToDtos(List<AppUser> list) {
        return list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDto mapToDto(AppUser appUser) {
        return AppUserDto.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .email(appUser.getEmail())
                .firstname(appUser.getFirstname())
                .lastname(appUser.getLastname())
                .street(appUser.getStreet())
                .zipCode(appUser.getZipCode())
                .city(appUser.getCity())
                .build();
    }
}
