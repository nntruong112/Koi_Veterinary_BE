package com.KoiHealthService.Koi.demo.controller;



import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.request.VerifyRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {

    UserService userService;

    //Register
    @PostMapping("/register")
    ApiResponse<UserResponse> Register(@RequestBody @Valid UserRequest userRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.Register(userRequest));

        return apiResponse;
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyRequest verifyRequest){
        boolean valid = userService.VerifyCode(verifyRequest.getEmail(), verifyRequest.getCode());
        if(valid){
            return ResponseEntity.ok("Mã xác minh hợp lệ");
        }else {
            return ResponseEntity.badRequest().body("Mã xác minh như lồn luôn em ơi");
        }
    }


    // Get all user
    @GetMapping("/get-user")
    ApiResponse<List<UserResponse>> getAllUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") String id){
        return userService.getById(id);
    }

    @GetMapping("myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }



    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable String id) {
        userService.DeleterUserByID(id);
        return "user has been deleted";
    }
    @DeleteMapping
    String DeleteAll(){
        userService.deleteAll();
        return "Delete thành công";
    }


}



