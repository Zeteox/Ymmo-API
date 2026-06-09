package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.controller.RegisterRequest;
import fr.loic_delprat.ymmo_api.dto.request.LoginRequest;
import fr.loic_delprat.ymmo_api.dto.response.AuthResponse;
import fr.loic_delprat.ymmo_api.entity.Agency;
import fr.loic_delprat.ymmo_api.entity.Role;
import fr.loic_delprat.ymmo_api.entity.RoleName;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.exeption.BadRequestException;
import fr.loic_delprat.ymmo_api.exeption.ConflictException;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.AgencyRepository;
import fr.loic_delprat.ymmo_api.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private final AgencyRepository agencyRepository;

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

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("A user with this email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ConflictException("A user with this phone number already exists");
        }

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found"));
        Agency agency = agencyRepository.findById(request.getAgencyId())
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + request.getAgencyId()));

        String token = jwtService.generateToken(
                request.getEmail(),
                role.getName().name()
        );

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(role)
                .agency(agency)
                .isActive(true)
                .build();

        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getRole().getName().name()
        );
    }
}
