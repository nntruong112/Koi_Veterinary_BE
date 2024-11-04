package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.LoginGoogleRequest;
import com.KoiHealthService.Koi.demo.dto.response.LoginResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import com.KoiHealthService.Koi.demo.service.AuthenticateService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/googles")
@RequiredArgsConstructor
public class GoogleController {

    final UserRepository userRepository;

    final AuthenticateService authenticateService;

    @PostMapping("/login-google")
    public ResponseEntity<LoginResponse> loginGoogle(@RequestBody LoginGoogleRequest request) {
        String accessToken = request.getAccessToken(); // Lấy accessToken từ request

        // Thêm access_token vào URL
        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Thực hiện yêu cầu API đến Google
            ResponseEntity<Map> response = restTemplate.getForEntity(userInfoEndpoint, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = response.getBody();

                // Trích xuất tên và email từ phản hồi
                String name = (String) userInfo.get("name");
                String email = (String) userInfo.get("email");

                // Lưu thông tin người dùng
                LoginResponse loginResponse =  authenticateService.loginGoogle(email,name);
                // Tạo LoginResponse với token và thông tin người dùng
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (RestClientException | JOSEException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}