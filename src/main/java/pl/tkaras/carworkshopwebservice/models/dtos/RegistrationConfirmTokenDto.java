package pl.tkaras.carworkshopwebservice.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegistrationConfirmTokenDto {

    private String token;

    private LocalDateTime createdOn;

    private LocalDateTime expiresOn;

    private LocalDateTime confirmedOn;
}
