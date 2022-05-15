package pl.tkaras.carworkshopwebservice.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RegistrationConfirmTokenDto {

    private String token;

    private LocalDateTime createdOn;

    private LocalDateTime expiresOn;

    private LocalDateTime confirmedOn;
}
