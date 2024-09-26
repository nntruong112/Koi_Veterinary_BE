package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    JavaMailSender javaMailSender;
    @NonFinal
    @Value("${spring.mail.username}")
    String SENDER_EMAIL;

    // Register
    public UserResponse Register(UserRequest userRequest) {
        //Find exist username
        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new AnotherException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userRequest);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void testSendEmail(UserRequest userRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(userRequest.getEmail());
        simpleMailMessage.setSubject("TestEmail");
        simpleMailMessage.setText("Yeah");
        simpleMailMessage.setFrom(SENDER_EMAIL);
        javaMailSender.send(simpleMailMessage);
    }

    // Get All User
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    // Get User By Id
    public UserResponse getById(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find the id")));
    }




}
