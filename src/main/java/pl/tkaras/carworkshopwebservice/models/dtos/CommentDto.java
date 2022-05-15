package pl.tkaras.carworkshopwebservice.models.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentDto {

    @ApiModelProperty(readOnly = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String description;

    @ApiModelProperty(readOnly = true, required = true)
    private String username;

    @ApiModelProperty(readOnly = true)
    private LocalDateTime createdOn;
}
