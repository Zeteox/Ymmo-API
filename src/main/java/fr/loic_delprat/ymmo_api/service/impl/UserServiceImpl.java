package fr.loic_delprat.ymmo_api.service.impl;

import fr.loic_delprat.ymmo_api.dto.response.UserResponse;
import fr.loic_delprat.ymmo_api.entity.User;
import fr.loic_delprat.ymmo_api.exeption.ResourceNotFoundException;
import fr.loic_delprat.ymmo_api.repository.UserRepository;
import fr.loic_delprat.ymmo_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapToResponse(user);
    }

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getIsActive(),
                user.getRole().getName().name()
        );
    }
}