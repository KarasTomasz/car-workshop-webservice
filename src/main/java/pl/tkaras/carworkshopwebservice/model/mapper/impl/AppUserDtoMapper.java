package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.AppUserDto;
import pl.tkaras.carworkshopwebservice.model.entity.AppUser;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserDtoMapper implements DtoMapper<AppUser, AppUserDto> {

    public AppUserDtoMapper(){ };

    @Override
    public List<AppUserDto> mapToDtos(List<AppUser> list) {
        return list.stream()
                .map(user -> mapToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDto mapToDto(AppUser appUser) {
        return AppUserDto.builder()
                .username(appUser.getUsername())
                .userRole(appUser.getRole())
                .isAccountNonExpired(appUser.isAccountNonExpired())
                .isAccountNonLocked(appUser.isAccountNonLocked())
                .isCredentialsNonExpired(appUser.isCredentialsNonExpired())
                .isEnabled(appUser.isEnabled())
                .build();
    }
}
