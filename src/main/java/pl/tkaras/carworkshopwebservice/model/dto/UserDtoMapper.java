package pl.tkaras.carworkshopwebservice.model.dto;

import pl.tkaras.carworkshopwebservice.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoMapper {

    private UserDtoMapper(){ };

    public static List<UserDto> mapToUserDtos(List<User> users){
        return users.stream()
                .map(user -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public static UserDto mapToUserDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .userRank(user.getUserRank())
                .build();
    }
}
