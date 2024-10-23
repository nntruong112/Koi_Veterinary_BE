package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.config.EmailConfig;
import com.KoiHealthService.Koi.demo.config.VNPayConfig;
import com.KoiHealthService.Koi.demo.dto.request.PaymentRequest;
import com.KoiHealthService.Koi.demo.dto.response.PaymentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Payment;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.repository.PaymentRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import com.KoiHealthService.Koi.demo.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
    private final VNPayConfig vnPayConfig;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final EmailConfig emailConfig;

    @PostMapping("/create-payment")
    public PaymentResponse createPayment(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        return paymentService.createPayment(request, paymentRequest);
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<PaymentResponse> payCallbackHandler(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_PayDate") String payDate,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_TxnRef") String txnRef) {

        if ("00".equals(responseCode)) {
            // Payment successful

            // Decode orderInfo
            String decodedOrderInfo;
            try {
                decodedOrderInfo = URLDecoder.decode(orderInfo, StandardCharsets.UTF_8.name());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(PaymentResponse.builder()
                        .message("Error decoding order info: " + e.getMessage())
                        .build());
            }

            // Split orderInfo to get userId, username, and email (now using "|" as a delimiter)
            String[] orderDetails = decodedOrderInfo.split("\\|");
            if (orderDetails.length < 5) {
                return ResponseEntity.badRequest().body(PaymentResponse.builder()
                        .message("Invalid order info format")
                        .build());
            }

            String userId = orderDetails[0]; // First element is userId
            String username = orderDetails[1]; // Second element is username
            String email = orderDetails[2]; // Third element is email
            String appointmentId = orderDetails[3];
            String orderType = orderDetails[4];

            // Convert amount from String to Long
            long amountValue;
            try {
                amountValue = Long.parseLong(amount) / 100; // Convert back to original amount
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(PaymentResponse.builder()
                        .message("Invalid amount format")
                        .build());
            }
            // Create Payment object
            Payment payment = Payment.builder()
                    .user(User.builder()
                            .userId(userId)
                            .build())
                    .appointment(Appointment.builder()
                            .appointmentId(appointmentId)
                            .build())
                    .amountValue(amountValue)
                    .vnp_PayDate(payDate)
                    .vnp_TxnRef(txnRef)
                    .name(username)
                    .email(email)
                    .orderType(orderType)
                    .build();

            // Save Payment to the database
            Payment savedPayment = paymentRepository.save(payment);

            emailConfig.sendInvoiceEmail(email, payment);

            // Return paymentId in the response
            String redirectUrl = "http://localhost:5173/get-payments/" +payment.getPaymentId(); // Thay đổi URL này thành URL của bạn
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
        } else {
            // Payment failed
            String redirectUrl = "http://localhost:5173/member/my-appointment/paymentPage"; // Thay đổi URL này thành URL của bạn
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(redirectUrl))
                    .build();
        }
    }


    @GetMapping("/get-payments/{paymentId}")
    public Payment getPayment(@PathVariable String paymentId) {
        return paymentService.getPayment(paymentId);
    }
}

