package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.UserDto;
import pl.tkaras.carworkshopwebservice.model.entity.User;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper implements DtoMapper<User, UserDto> {

    public UserDtoMapper(){ };

    @Override
    public List<UserDto> mapToDtos(List<User> list) {
        return list.stream()
                .map(user -> mapToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .userRank(user.getUserRank())
                .build();
    }
}
