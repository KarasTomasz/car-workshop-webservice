package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDetailsDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUserDetails;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserDetailsDtoMapper implements DtoMapper<AppUserDetails, AppUserDetailsDto> {

    public AppUserDetailsDtoMapper(){ }

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
}
