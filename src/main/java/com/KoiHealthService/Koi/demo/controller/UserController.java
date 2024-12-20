package com.KoiHealthService.Koi.demo.controller;



import com.KoiHealthService.Koi.demo.config.EmailConfig;
import com.KoiHealthService.Koi.demo.dto.request.ForgotPasswordRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UserRequest;
import com.KoiHealthService.Koi.demo.dto.request.VerifyRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.ForgotPasswordResponse;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {


    final UserService userService;

    final EmailConfig emailConfig;

    //Register
    @PostMapping("/register")
    ApiResponse<UserResponse> Register(@RequestBody @Valid UserRequest userRequest) {

        return ApiResponse.<UserResponse>builder()
                .result(userService.register(userRequest))
                .build();
    }

    //Test verify email
    @PostMapping("/verify")
    public ApiResponse<UserResponse> verifyCode(@RequestBody VerifyRequest verifyRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.verifyCode(verifyRequest.getCode()))
                .build();
    }

    //Create Role STAFF
    @PostMapping("/create/staff")
    public ApiResponse<UserResponse>  createRoleStaff(@RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createStaff(userRequest))
                .build();
    }

    @PostMapping("/create/vet")
    public ApiResponse<UserResponse>  createRoleVet(@RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createVet(userRequest))
                .build();
    }

    // Get all user
    @GetMapping("/get-user")
    ApiResponse<List<UserResponse>> getAllUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    // Get user by Id
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") String id){
        return userService.getById(id);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity <String>forgotPassword(@RequestParam String email){
        try {
            userService.forgotPassword(email);
            return ResponseEntity.status(HttpStatus.OK).body("Reset password email has been sent to " + email);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ApiResponse<ForgotPasswordResponse> resetPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        return ApiResponse.<ForgotPasswordResponse>builder()
                .result(userService.ResetPassword(forgotPasswordRequest))
                .build();
    }

    // Update user by Id
    @PutMapping ("/{id}")
    UserResponse updateUser(@PathVariable ("id") String id, @RequestBody UpdateRequest updateRequest){
        return userService.updateUser(id,updateRequest);
    }

    // Delete User by ID
    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable String id) {
        userService.deleterUserByID(id);
        return "user has been deleted";
    }

    //Delete All User
    @DeleteMapping
    String DeleteAll(){
        userService.deleteAll();
        return "Delete thành công";
    }

    @GetMapping("/role/{roles}")
    public ApiResponse<List<User>> getByRole(@PathVariable String roles){
        return ApiResponse.<List<User>>builder()
                .result(userService.getByRole(roles))
                .build();
    }

    @GetMapping("/countByRole")
    public Long getCountByRole(@RequestParam String role) {
        try {
            return userService.getCountByRole(role);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found", e);
        }
    }

    @PostMapping("/late-penalty/{userId}")
    public ResponseEntity<Void> applyLatePenalty(@PathVariable String userId) {
        userService.latePenalty(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/absent-penalty/{userId}")
    public ResponseEntity<Void> applyAbsentPenalty(@PathVariable String userId) {
        userService.absentPenalty(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bonus-rating/{userId}")
    public ResponseEntity<Void> bonusRating(@PathVariable String userId) {
        userService.bonusRating(userId);
        return ResponseEntity.ok().build();
    }


}