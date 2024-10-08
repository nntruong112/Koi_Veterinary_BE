package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.AuthenticationRequest;
import com.KoiHealthService.Koi.demo.dto.request.IntrospectRequest;
import com.KoiHealthService.Koi.demo.dto.request.LogoutRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.AuthenticationResponse;
import com.KoiHealthService.Koi.demo.dto.response.IntrospectResponse;
import com.KoiHealthService.Koi.demo.service.AuthenticateService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    @NonNull
    AuthenticateService authenticateService;


    //Login
    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws JOSEException {
        var result = authenticateService.authenticated(authenticationRequest);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    //Logout

    @PostMapping("/logout")
    ApiResponse<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticateService.logout(request);
        return ApiResponse.<String>builder()
                .result("Logout successfully")
                .build();

    }

    //Verify Token
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        var result = authenticateService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();


    }
}
