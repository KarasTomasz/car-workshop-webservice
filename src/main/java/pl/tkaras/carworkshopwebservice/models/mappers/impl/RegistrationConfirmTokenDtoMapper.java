package pl.tkaras.carworkshopwebservice.models.mappers.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.models.dtos.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.models.mappers.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistrationConfirmTokenDtoMapper implements DtoMapper<RegistrationConfirmToken, RegistrationConfirmTokenDto> {


    @Override
    public List<RegistrationConfirmTokenDto> mapToDtos(List<RegistrationConfirmToken> list) {
        return list.stream()
                .map(token -> mapToDto(token))
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationConfirmTokenDto mapToDto(RegistrationConfirmToken confirmToken) {
        return RegistrationConfirmTokenDto.builder()
                .token(confirmToken.getToken())
                .createdOn(confirmToken.getCreatedOn())
                .expiresOn(confirmToken.getExpiredOn())
                .confirmedOn(confirmToken.getConfirmedOn())
                .build();
    }
}
