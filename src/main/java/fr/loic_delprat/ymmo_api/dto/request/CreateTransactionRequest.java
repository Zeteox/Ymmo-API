package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionRequest {

    @NotNull
    private Long buildingId;

    @NotNull
    private Long buyerId;

    @NotNull
    private Long agentId;

    @NotNull
    private Long agencyId;

    @NotNull
    @Min(0)
    private Float amount;
}
