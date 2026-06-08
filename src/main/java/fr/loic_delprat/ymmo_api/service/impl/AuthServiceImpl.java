package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.request.LoginRequest;
import fr.loic_delprat.ymmo_api.dto.response.AuthResponse;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.exeption.BadRequestException;
import fr.loic_delprat.ymmo_api.repository.UserRepository;
import fr.loic_delprat.ymmo_api.security.JwtService;
import fr.loic_delprat.ymmo_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().getName().name()
        );

        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getRole().getName().name()
        );
    }
}
