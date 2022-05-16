package pl.tkaras.carworkshopwebservice.models.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarDto {

    @ApiModelProperty(readOnly = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String mark;

    @ApiModelProperty(required = true)
    private String model;

    private String description;
}
