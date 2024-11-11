package com.microservice.building_be.controller;


import com.microservice.building_be.dto.request.LoginRequest;
import com.microservice.building_be.dto.response.LoginResponse;
import com.microservice.building_be.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AuthController {
    @Autowired
    private AuthService authService;

//    @Autowired
//    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody @Valid LoginRequest request){
        LoginResponse result = authService.handleLogin(request);
        result.setMessage("Login successful");
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        String token = extractTokenFromRequest(request); // Hàm lấy token từ request
//
//        // Thêm token vào danh sách đen
//        tokenBlacklistService.addTokenToBlacklist(token);
//
//        return ResponseEntity.ok("Logout successful");
//    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Giả sử token nằm trong header "Authorization"
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

