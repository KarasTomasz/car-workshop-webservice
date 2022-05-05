package pl.tkaras.carworkshopwebservice.models.mappers.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUser;
import pl.tkaras.carworkshopwebservice.models.mappers.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserMapper implements DtoMapper<AppUser, AppUserDto> {

    public AppUserMapper(){ }

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

    @Override
    public List<AppUser> mapToEntities(List<AppUserDto> list) {
        return list.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AppUser mapToEntity(AppUserDto appUserDto) {
        return AppUser.builder()
                .username(appUserDto.getUsername())
                .email(appUserDto.getEmail())
                .firstname(appUserDto.getFirstname())
                .lastname(appUserDto.getLastname())
                .street(appUserDto.getStreet())
                .zipCode(appUserDto.getZipCode())
                .city(appUserDto.getCity())
                .build();
    }
}
