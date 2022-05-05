package pl.tkaras.carworkshopwebservice.models.mappers.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.models.dtos.AppUserDetailsDto;
import pl.tkaras.carworkshopwebservice.models.entities.AppUserDetails;
import pl.tkaras.carworkshopwebservice.models.mappers.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserDetailsMapper implements DtoMapper<AppUserDetails, AppUserDetailsDto> {

    public AppUserDetailsMapper(){ }

    @Override
    public List<AppUserDetailsDto> mapToDtos(List<AppUserDetails> list) {
        return list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDetailsDto mapToDto(AppUserDetails appUserDetails) {
        return AppUserDetailsDto.builder()
                .username(appUserDetails.getAppUser().getUsername())
                .isAccountNonExpired(appUserDetails.isAccountNonExpired())
                .isAccountNonLocked(appUserDetails.isAccountNonLocked())
                .isCredentialsNonExpired(appUserDetails.isCredentialsNonExpired())
                .isEnabled(appUserDetails.isEnabled())
                .userRole(appUserDetails.getRole())
                .build();
    }

    @Override
    public List<AppUserDetails> mapToEntities(List<AppUserDetailsDto> list) {
        return list.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDetails mapToEntity(AppUserDetailsDto appUserDetailsDto) {
        return AppUserDetails.builder()
                .isAccountNonExpired(appUserDetailsDto.isAccountNonExpired())
                .isAccountNonLocked(appUserDetailsDto.isAccountNonLocked())
                .isCredentialsNonExpired(appUserDetailsDto.isCredentialsNonExpired())
                .isEnabled(appUserDetailsDto.isEnabled())
                .role(appUserDetailsDto.getRole())
                .build();

    }
}
