
package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.Storage.UserStorage;
import com.KoiHealthService.Koi.demo.config.EmailConfig;
import com.KoiHealthService.Koi.demo.dto.request.ForgotPasswordRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.ForgotPasswordResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.FishSpecialty;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.FishSpecialtyMapper;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
import com.KoiHealthService.Koi.demo.repository.FishSpecialtyRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @NonNull
    EmailConfig emailConfig;

    @NonNull
    UserStorage userStorage;

    @NonNull
    UserRepository userRepository;

    @NonNull
    UserMapper userMapper;
    @NonNull
    PasswordEncoder passwordEncoder;
    @NonNull
    JavaMailSender javaMailSender;
    @NonFinal
    @Value("${spring.mail.username}")
    String SENDER_EMAIL;

    @NonNull
    final FishSpecialtyRepository fishSpecialtyRepository;

    @NonNull
    final FishSpecialtyMapper fishSpecialtyMapper;


     

    // Register
    public UserResponse register(UserRequest userRequest) {
//        Find exist username
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AnotherException(ErrorCode.USER_EXISTED);
        }

        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new AnotherException(ErrorCode.EXISTED_EMAIL);
        }
        //Mapping request to database
        User user = userMapper.toUser(userRequest);

        //Encryption password
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Set verification code expiration time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1); // adjust the expiration time as needed
        user.setVerificationCodeExpiration(calendar.getTime());

        //get verify code
        String verificationCode = generateCode();
        user.setVerificationCode(verificationCode);

        //Set Role
        user.setRoles("USER");
        user.setCheckIsLoginGoogle(false);
        emailConfig.sendCode(userRequest.getEmail(), "KoiHealthSerivce@gmail.com", "Mã xác minh của bạn là : " + verificationCode);

        //Save info user and code into userStorage
        userStorage.storeVerificationCode(verificationCode, user);
        userStorage.storeEmail(userRequest.getEmail(), user);

        return userMapper.toUserResponse(user);
    }

    // Role VET
    public UserResponse createVet(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AnotherException(ErrorCode.USER_EXISTED);
        }
        FishSpecialty fishSpecialty = fishSpecialtyRepository.findById(userRequest.getFishSpecialtyId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_SPECIALTY_FOUND));

        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles("VET");
        user.setRating(10);
        user.setFishSpecialty(fishSpecialty);


        return userMapper.toUserResponse(userRepository.save(user));
    }

    // Role Staff
    public UserResponse createStaff(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AnotherException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles("STAFF");
        return userMapper.toUserResponse(userRepository.save(user));
    }

    //Update User
    public UserResponse updateUser(String id, UpdateRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User is not found"));

        userMapper.toUpdateUser(user, updateRequest);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    // Get All User
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }


    //Get info user
    @PreAuthorize("hasRole('USER')")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
    //Forgot Password
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AnotherException(ErrorCode.EMAIL_NOT_EXISTED));

        String resetLink =  "http://localhost:5173/reset-password?email=" + email;
        emailConfig.sendResetPasswordEmail(email, resetLink);
    }

    //Verify Code
    public UserResponse verifyCode(String verificationCode) {
        // Take the code from userStorage
        User user = userStorage.getUserByVerificationCode(verificationCode);
        if (user != null && new Date().before(user.getVerificationCodeExpiration())) {
            // user will save into database if code is right
            return userMapper.toUserResponse(userRepository.save(user));
        } else
            // if code wrong throw exception
            throw new AnotherException(ErrorCode.INVALID_CODE);
    }

    //Reset Password
    public ForgotPasswordResponse ResetPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).orElseThrow(() -> new AnotherException(ErrorCode.EMAIL_NOT_EXISTED));

        user.setPassword(passwordEncoder.encode(forgotPasswordRequest.getNewPassword()));
        userRepository.save(user);

        return ForgotPasswordResponse.builder()
                .user(user)
                .build();
    }

    // Get User By Id
    @PreAuthorize("hasRole('USER')")
    public UserResponse getById(String id) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find the id")));
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleterUserByID(String id) {
        userRepository.deleteById(id);
    }

    public String generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // generate a 6-digit number between 100000 and 999999
        return String.valueOf(code);
    }

    //Get user by Role
    public List<User> getByRole(String roles) {
        if (roles != null) {
            return userRepository.findByRoles(roles);
        } else {
            throw new RuntimeException("Cannot find  role");
        }
    }

    //count the role
    public long getCountByRole(String roles) {
        if (roles != null) {
            return userRepository.countByRoles(roles);
        } else {
            throw new RuntimeException("Cannot find role");
        }
    }

    //minus rating if late
    public void latePenalty(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Decrease rating by 1 if it's greater than 0
        if (user.getRating() != null && user.getRating() > 0) {
            user.setRating(user.getRating() - 1);
        } else {
            user.setRating(0); // Prevent negative rating
        }

        userRepository.save(user); // Save updated user
    }

    public void absentPenalty(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRating() != null && user.getRating() > 0) {
            user.setRating(Math.max(user.getRating() - 3, 0));
        }

        userRepository.save(user);
    }
}
