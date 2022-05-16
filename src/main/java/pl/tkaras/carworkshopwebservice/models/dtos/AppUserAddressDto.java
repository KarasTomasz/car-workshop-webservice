package pl.tkaras.carworkshopwebservice.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppUserAddressDto {

    private String firstName;

    private String lastName;

    private String street;

    private String zipCode;

    private String city;
}