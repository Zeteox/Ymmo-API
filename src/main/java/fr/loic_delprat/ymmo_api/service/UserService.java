package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse getCurrentUser(String email);
}
