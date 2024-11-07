package com.KoiHealthService.Koi.demo.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.KoiHealthService.Koi.demo.Storage.UserStorage;
import com.KoiHealthService.Koi.demo.config.EmailConfig;
import com.KoiHealthService.Koi.demo.dto.request.feedback.FeedbackCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UpdateRequest;
import com.KoiHealthService.Koi.demo.dto.request.user.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Feedback;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.FeedbackRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import com.KoiHealthService.Koi.demo.service.AuthenticateService;
import com.KoiHealthService.Koi.demo.service.FeedbackService;
import com.KoiHealthService.Koi.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    FeedbackRepository feedbackRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailConfig emailConfig;

    @InjectMocks
    FeedbackService feedbackService;

    @Mock
    private UserStorage userStorage;

    @Mock
    private AuthenticateService authenticateService;

    @InjectMocks
    private UserService userService;


    @Test
    public void testRegister_Success() {
        UserRequest userRequest = UserRequest.builder()
                .username("nguyenvuong23112004")
                .password("123456789")
                .email("nguyenvuong23112004@gmail.com")
                .lastname("vuongtran23112004")
                .build();
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());

        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userMapper.toUserResponse(user)).thenReturn(new UserResponse());

        UserResponse userResponse = userService.register(userRequest);

        assertNotNull(userResponse);
        verify(emailConfig).sendCode(eq(userRequest.getEmail()), anyString(), anyString());
    }

    @Test
    public void testRegister_UserExists() {
        UserRequest userRequest = UserRequest.builder()
                .username("vuong23112004")
                .password("12345678")
                .build();

        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(true);

        Exception exception = assertThrows(AnotherException.class, () -> {
            userService.register(userRequest);
        });

        assertEquals(ErrorCode.USER_EXISTED, ((AnotherException) exception).getErrorCode());
    }

    @Test
    void updateUser_HappyCase() {
        // Given
        String userId = "123";
        UpdateRequest updateRequest = new UpdateRequest();
        User existingUser = new User();
        User updatedUser = new User();
        UserResponse expectedResponse = new UserResponse();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(userMapper).toUpdateUser(existingUser, updateRequest);
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toUserResponse(updatedUser)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.updateUser(userId, updateRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findById(userId);
        verify(userMapper).toUpdateUser(existingUser, updateRequest);
        verify(userRepository).save(existingUser);
        verify(userMapper).toUserResponse(updatedUser);
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        String userId = "21156622-7146-42d8-9739-6ff5c5622347";
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setLastname("Vuong");
        updateRequest.setFirstname("Nguyen");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, updateRequest);
        });

        assertEquals("User is not found", exception.getMessage());
        verify(userRepository).findById(userId);
        verify(userMapper, never()).toUpdateUser(any(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testCreateFeedbackSuccess() {
        String feedbackId = "12345";
        String comment = "Excellent service!";
        int rating = 5;
        String punctuality = "On time";
        String appointmentId = "acffffvvvff";
        String customerId = "fd3e896e-2b88-4607-bc77-01a1e4f09ae5";

        FeedbackCreationRequest request = new FeedbackCreationRequest(feedbackId, comment, rating, customerId, appointmentId, punctuality);

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);

        User customer = new User();
        customer.setUserId(customerId);

        Feedback feedback = Feedback.builder()
                .feedbackId(feedbackId)
                .comment(comment)
                .rating(rating)
                .appointment(appointment)
                .customer(customer)
                .punctuality(punctuality)
                .build();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback result = feedbackService.createFeedback(request);

        assertNotNull(result);
        assertEquals(feedbackId, result.getFeedbackId());
        assertEquals(comment, result.getComment());
        assertEquals(rating, result.getRating());
        assertEquals(punctuality, result.getPunctuality());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));  // Verify save is called once
    }

    @Test
    void testCreateFeedbackNoAppointmentFound() {
        // Arrange
        String appointmentId = "acffffvvvff";
        String customerId = "fd3e896e-2b88-4607-bc77-01a1e4f09ae5";

        FeedbackCreationRequest request = new FeedbackCreationRequest("12345", "Excellent service!", 5, "On time", appointmentId, customerId);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());  // Appointment not found

        AnotherException exception = assertThrows(AnotherException.class, () -> {
            feedbackService.createFeedback(request);
        });
        assertEquals(ErrorCode.NO_APPOINTMENT_FOUND, exception.getErrorCode());
    }


}
