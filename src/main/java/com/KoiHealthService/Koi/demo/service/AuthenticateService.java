package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.AuthenticationRequest;
import com.KoiHealthService.Koi.demo.dto.request.IntrospectRequest;
import com.KoiHealthService.Koi.demo.dto.request.LoginRequest;
import com.KoiHealthService.Koi.demo.dto.request.LogoutRequest;
import com.KoiHealthService.Koi.demo.dto.response.AuthenticationResponse;
import com.KoiHealthService.Koi.demo.dto.response.IntrospectResponse;
import com.KoiHealthService.Koi.demo.dto.response.LoginResponse;
import com.KoiHealthService.Koi.demo.entity.InvalidatedToken;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.repository.InvalidatedTokenRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateService {

    UserRepository userRepository;

    InvalidatedTokenRepository invalidatedTokenRepository;

    //Key to verify token
    @NonFinal
    @Value("${jwt.signer_key}")
    public String SIGNER_KEY;


    //Login
    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) throws JOSEException {
        // Find user by username
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Check password
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AnotherException(ErrorCode.LOGIN_FAILED);
        }
        //Generate Token
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    //Login google
    public LoginResponse loginGoogle(String email, String name) throws JOSEException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().isCheckIsLoginGoogle()) {
            String token = generateToken(user.get());
            return LoginResponse.builder()
                    .token(token)
                    .user(user.get())
                    .build();
        } else {
            User newUser = User.builder()
                    .email(email)
                    .username(name)
                    .roles("USER")
                    .checkIsLoginGoogle(true)
                    .build();
            userRepository.save(newUser);
            String token = generateToken(newUser);

            return LoginResponse.builder()
                    .token(token)
                    .user(newUser)
                    .build();
        }
    }


    //Verify Token
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        //Get token
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token);
        } catch (AnotherException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();

    }

    //Generate token
    public String generateToken(User user) throws JOSEException {
        //Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRoles())
                .build();
        // Payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }

    }

    //Verify Token
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        //Parse token
        SignedJWT signedJWT = SignedJWT.parse(token);
        //Verify token
        var verified = signedJWT.verify(verifier);
        //Verify Expiration time
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AnotherException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AnotherException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    //Log Out
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jwtID = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwtID)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }
}