package fr.loic_delprat.ymmo_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private Long buildingId;
    private String buildingName;
    private Long buyerId;
    private String buyerFullName;
    private Long agentId;
    private String agentFullName;
    private Long agencyId;
    private String agencyName;
    private float amount;
}
