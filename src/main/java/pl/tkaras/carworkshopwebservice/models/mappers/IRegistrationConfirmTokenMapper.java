package pl.tkaras.carworkshopwebservice.models.mappers;

import org.mapstruct.Mapper;
import pl.tkaras.carworkshopwebservice.models.dtos.RegistrationConfirmTokenDto;
import pl.tkaras.carworkshopwebservice.models.entities.RegistrationConfirmToken;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRegistrationConfirmTokenMapper {

    List<RegistrationConfirmTokenDto> mapToDtos(List<RegistrationConfirmToken> list);

    RegistrationConfirmTokenDto mapToDto(RegistrationConfirmToken confirmToken);

    List<RegistrationConfirmToken> mapToEntities(List<RegistrationConfirmTokenDto> list);

    RegistrationConfirmToken mapToEntity(RegistrationConfirmTokenDto confirmTokenDTO);

}