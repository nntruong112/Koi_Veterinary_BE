package com.KoiHealthService.Koi.demo.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.KoiHealthService.Koi.demo.Enum.Role;
import com.KoiHealthService.Koi.demo.Storage.UserStorage;
import com.KoiHealthService.Koi.demo.dto.request.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
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


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class UserService {

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

    // Register
    public UserResponse Register(UserRequest userRequest) {
//        Find exist username

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AnotherException(ErrorCode.USER_EXISTED);
        }
        //Mapping request to database
        User user = userMapper.toUser(userRequest);

        //Encryption password
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Set verification code expiration time (e.g., 30 minutes from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1); // adjust the expiration time as needed
        user.setVerificationCodeExpiration(calendar.getTime());

        //get verify code
        String verificationCode = generateCode();
        user.setVerificationCode(verificationCode);

        //Set Role
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        //Send Mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userRequest.getEmail());
        message.setText("Mã xác minh của bạn là :" + verificationCode);
        javaMailSender.send(message);

        //Save info user and code into userStorage
        userStorage.storeVerificationCode(verificationCode,user);
        userStorage.storeEmail(userRequest.getEmail(),user);


        return userMapper.toUserResponse(user);
    }


    //Update User
    public UserResponse UpdateUser(String id, UpdateRequest updateRequest){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User is not found") );

        userMapper.toUpdateUser(user,updateRequest);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    // Get All User
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }


    //Get info user
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    //Verify Code
    public UserResponse VerifyCode(String verificationCode) {
          // Take the code from userStorage
    User user = userStorage.getUserByVerificationCode(verificationCode);
    if (user != null && new Date().before(user.getVerificationCodeExpiration()) ) {
        // user will save into database if code is right
        return userMapper.toUserResponse(userRepository.save(user));
    } else
        // if code wrong throw exception
        throw  new AnotherException(ErrorCode.INVALID_CODE);
    }

    //Send verify-code again
//    public UserResponse sendVerifyCodeAgain(String email) {
//    // Check if a verification code already exists for the user
//    User user = userStorage.getUserEmail(email);
//    if (user != null && user.getVerificationCode() != null && user.getVerificationCodeExpiration() != null) {
//        Date currentTime = new Date();
//        if (currentTime.before(user.getVerificationCodeExpiration())) {
//            // Code is still valid, don't send a new one
//            throw new AnotherException(ErrorCode.VERIFICATION_CODE_ALREADY_SENT);
//        }
//    }
//
//    // Generate a new verification code and send it
//    String verificationCode = generateCode();
//    SimpleMailMessage message = new SimpleMailMessage();
//    message.setTo(email);
//    message.setText("Mã xác minh của bạn là :" + verificationCode);
//    javaMailSender.send(message);
//    // Update the user's verification code and expiration time
//    user.setVerificationCode(verificationCode);
//    user.setVerificationCodeExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes
//    userRepository.save(user); //
//
//    return userMapper.toUserResponse(user);
//}

    // Get User By Id
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getById(String id) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find the id")));
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public void DeleterUserByID(String id) {
        userRepository.deleteById(id);
    }

    public String generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // generate a 6-digit number between 100000 and 999999
        return String.valueOf(code);
    }

}





















