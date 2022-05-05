package pl.tkaras.carworkshopwebservice.models.mappers.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.models.dtos.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.models.mappers.DtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistrationConfirmTokenMapper implements DtoMapper<RegistrationConfirmToken, RegistrationConfirmTokenDto> {


    @Override
    public List<RegistrationConfirmTokenDto> mapToDtos(List<RegistrationConfirmToken> list) {
        return list.stream()
                .map(this::mapToDto)
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

    @Override
    public List<RegistrationConfirmToken> mapToEntities(List<RegistrationConfirmTokenDto> list) {
        return list.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationConfirmToken mapToEntity(RegistrationConfirmTokenDto registrationConfirmTokenDto) {
        return RegistrationConfirmToken.builder()
                .token(registrationConfirmTokenDto.getToken())
                .expiresOn(registrationConfirmTokenDto.getExpiresOn())
                .build();
    }
}
