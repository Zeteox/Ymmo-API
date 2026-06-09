package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgencyRequest {
    @NotBlank
    private String name;

    @NotNull
    private Long cityId;
}

