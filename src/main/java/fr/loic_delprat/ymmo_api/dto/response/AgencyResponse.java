package fr.loic_delprat.ymmo_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgencyResponse {
    private Long id;
    private String name;
    private String city;
}

