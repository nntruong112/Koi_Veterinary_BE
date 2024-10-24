package com.KoiHealthService.Koi.demo.controller;

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
public ResponseEntity<LoginResponse> loginGoogle(@RequestBody Map<String, String> requestBody) {
    String accessToken = requestBody.get("accessToken");
    String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken); // Set the Bearer token in the headers

    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {
        // Make the API request to Google
        ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = response.getBody();

            // Extract name and email from response
            String name = (String) userInfo.get("name");
            String email = (String) userInfo.get("email");

            // Save user information
            userRepository.save(User.builder()
                    .email(email)
                    .username(name)
                    .roles("USER")
                    .build());

            // Create LoginResponse with token and user info
            LoginResponse loginResponse  = authenticateService.loginGoogle(email,name);
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } catch (RestClientException | JOSEException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

}
