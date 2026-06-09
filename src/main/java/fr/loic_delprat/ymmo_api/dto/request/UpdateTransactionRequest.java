package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTransactionRequest {

    private Long buildingId;
    private Long buyerId;
    private Long agentId;
    private Long agencyId;

    @Min(0)
    private Float amount;
}
