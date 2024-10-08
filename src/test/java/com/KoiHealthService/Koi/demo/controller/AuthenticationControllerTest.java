package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.AuthenticationRequest;
import com.KoiHealthService.Koi.demo.dto.request.LoginRequest;
import com.KoiHealthService.Koi.demo.dto.response.AuthenticationResponse;
import com.KoiHealthService.Koi.demo.dto.response.LoginResponse;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.service.AuthenticateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    AuthenticationController authenticationController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthenticateService authenticateService;

    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    @BeforeEach
    public void initData() {
        authenticationRequest = AuthenticationRequest.builder()
                .username("admin")
                .password("12345")
                .build();
        authenticationResponse = AuthenticationResponse.builder()
                .token("eyJhbGciOiJIUzUxMiJ9.")
                .authenticated(true)
                .build();

    }

    @Test
    public void Login_Valid_Success() throws Exception {
        //Declare Mapper
        ObjectMapper mapper = new ObjectMapper();
        // Convert request to string
        String content = mapper.writeValueAsString(authenticationRequest);
        //When we have a request then return a response
        Mockito.when(authenticateService.authenticated(ArgumentMatchers.any()))
                .thenReturn(authenticationResponse);
        // Config mockMvc
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("result.token").value("eyJhbGciOiJIUzUxMiJ9."));

    }

    @Test
    public void Login_Invalid() throws Exception {
        //Change the password
        authenticationRequest.setPassword("1234");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(authenticationRequest);

        //Check Exception
        Mockito.when(authenticateService.authenticated(ArgumentMatchers.any()))
                .thenThrow(new AnotherException(ErrorCode.LOGIN_FAILED));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Invalid username or password")
                );

    }
}