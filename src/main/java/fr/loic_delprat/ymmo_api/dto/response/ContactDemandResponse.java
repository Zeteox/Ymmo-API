package fr.loic_delprat.ymmo_api.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDemandResponse {
    @NotNull
    private Long id;

    @NotNull
    Long userId;

    @NotNull
    Long buildingId;

    @NotBlank
    private String content;
}
