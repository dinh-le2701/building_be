package com.microservice.building_be.service;

import com.microservice.building_be.dto.request.LoginRequest;
import com.microservice.building_be.dto.response.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {

    LoginResponse handleLogin(LoginRequest loginRequest);

}
