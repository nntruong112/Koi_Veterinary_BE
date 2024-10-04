package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.AuthenticationRequest;
import com.KoiHealthService.Koi.demo.dto.request.IntrospectRequest;
import com.KoiHealthService.Koi.demo.dto.response.AuthenticationResponse;
import com.KoiHealthService.Koi.demo.dto.response.IntrospectResponse;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticateService {

        UserRepository userRepository;        

        //Key to verify token
        @NonFinal
        @Value("${jwt.signer_key}")
        protected String SIGNER_KEY;

    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) throws JOSEException {
            // Find user by username
            var user = userRepository.findByUsername(authenticationRequest.getUsername())
                    .orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));

           PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            // Check password
           boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(),user.getPassword());
            if(!authenticated){
                throw new AnotherException(ErrorCode.UNAUTHENTICATED);
            }
            var token = generateToken(authenticationRequest.getUsername());

            return AuthenticationResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();

        }

        public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
            //Get token
            var token = request.getToken();
            // Declare verifier
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            //Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);
            //Verify token
            var verified = signedJWT.verify(verifier);
            //Veriy
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            return  IntrospectResponse.builder()
                    .valid( verified && expiryTime.after(new Date()))
                    .build();

        }

        //Generate token
        private String generateToken(String username) throws JOSEException {
            //Header
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("")
                    .issueTime(new Date())
                    .expirationTime(new Date(
                            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                    ))
                    .claim("customClaim","Custom")
                    .build();
            // Payload
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header,payload);

            try {
                jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
                return jwsObject.serialize();
            } catch (JOSEException e) {
                log.error("Cannot create token");
                throw new RuntimeException(e);
            }

        }

}
