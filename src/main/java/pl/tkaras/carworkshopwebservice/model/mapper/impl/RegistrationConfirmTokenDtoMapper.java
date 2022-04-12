package pl.tkaras.carworkshopwebservice.model.mapper.impl;

import org.springframework.stereotype.Component;
import pl.tkaras.carworkshopwebservice.model.dto.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.model.entity.RegistrationConfirmToken;
import pl.tkaras.carworkshopwebservice.model.mapper.DtoMapper;

import javax.persistence.Column;
import java.time.LocalDateTime;
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
