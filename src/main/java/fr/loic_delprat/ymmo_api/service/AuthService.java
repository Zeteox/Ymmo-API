package fr.loic_delprat.ymmo_api.service;

import fr.loic_delprat.ymmo_api.dto.request.LoginRequest;
import fr.loic_delprat.ymmo_api.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}

