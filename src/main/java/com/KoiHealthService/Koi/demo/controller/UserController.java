package com.KoiHealthService.Koi.demo.controller;



import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {

     UserService userService;

     //Register
    @PostMapping("/register")
    UserResponse Register(@RequestBody  @Valid UserRequest userRequest){
        return userService.Register(userRequest);


    }
    // Test email
    @PostMapping("/test_email")
     void testSendMail (@RequestBody UserRequest userRequest){
        userService.testSendEmail(userRequest);
    }

    // Get all user
    @GetMapping("/Get_user")
    List<User> getAllUser(){
        return userService.getUsers();
    }


}
