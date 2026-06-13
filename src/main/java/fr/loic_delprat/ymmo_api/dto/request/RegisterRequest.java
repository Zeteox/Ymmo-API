package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(\\+33|0)[1-9](\\d{2}){4}$"
    )
    private String phone;

    @NotBlank
    @Size(min = 8)
    @Pattern(
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    )
    private String password;

    @NotNull
    private Long agencyId;
}
