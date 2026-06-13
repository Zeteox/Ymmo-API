package fr.loic_delprat.ymmo_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String type;
    private Long userId;
    private String email;
    private String role;
}
