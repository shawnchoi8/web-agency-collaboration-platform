package com.rdc.weflow_server.controller.auth;

import com.rdc.weflow_server.common.api.ApiResponse;
import com.rdc.weflow_server.dto.auth.request.LoginRequest;
import com.rdc.weflow_server.dto.auth.response.LoginResponse;
import com.rdc.weflow_server.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"https://www.weflow.kr", "https://weflow.kr"}, allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success("로그인에 성공했습니다.", response);
    }
}