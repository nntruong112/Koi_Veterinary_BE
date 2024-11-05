package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.config.EmailConfig;
import com.KoiHealthService.Koi.demo.config.VNPayConfig;
import com.KoiHealthService.Koi.demo.dto.request.PaymentRequest;
import com.KoiHealthService.Koi.demo.dto.response.PaymentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Payment;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.PaymentRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    VNPayConfig vnPayConfig;

    final EmailConfig emailConfig;

    final AppointmentRepository appointmentRepository;

    @NonNull
    UserRepository userRepository;

    @NonNull
    PaymentRepository paymentRepository;

    public PaymentResponse createPayment(HttpServletRequest request, PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        User user = userRepository.findById(paymentRequest.getUserId()).orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));
        Appointment appointment = appointmentRepository.findById(paymentRequest.getAppointmentId()).orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        String orderType = appointment.getAppointmentType().getAppointmentService();
        long amountValue = (long) ((appointment.getAppointmentType().getPrice() + appointment.getMovingFee())* 100);
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountValue));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", String.format("%s|%s|%s|%s|%s", user.getUserId(), user.getUsername(), user.getEmail(), appointment.getAppointmentId(), orderType));
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;



        return PaymentResponse.builder()
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("error"));
    }

    public Long getAmountValueInMonth(int month) {
        return paymentRepository.getAmountValueInMonth(month);
    }
}
