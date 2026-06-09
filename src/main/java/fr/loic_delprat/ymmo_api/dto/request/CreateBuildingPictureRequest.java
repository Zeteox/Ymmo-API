package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBuildingPictureRequest {

    @NotBlank
    private String path;
}
