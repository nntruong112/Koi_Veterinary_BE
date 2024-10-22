package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.NonNull;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/googles")
public class GoogleController {

    private final UserRepository userRepository;

    public GoogleController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @PostMapping("/login-google")
public ResponseEntity<Map<String, String>> loginGoogle(@RequestBody Map<String, String> requestBody) {
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


            // Create a response map to return as JSON
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("name", name);
            responseMap.put("email", email);


            return ResponseEntity.ok(responseMap);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } catch (RestClientException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
}
