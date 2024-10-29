package com.KoiHealthService.Koi.demo.config;

import com.KoiHealthService.Koi.demo.entity.Payment;
import com.KoiHealthService.Koi.demo.repository.PaymentRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class EmailConfig {


    final PaymentRepository paymentRepository;

    final JavaMailSender javaMailSender;

    @NonFinal
    @Value("${spring.mail.username}")
    String SENDER_EMAIL;

    public void sendCode(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendInvoiceEmail(String to, Payment payment) {
        String subject = "Hóa đơn #" + payment.getEmail();

        // Định dạng giá trị thanh toán
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedAmount = formatter.format(payment.getAmountValue());

        // Định dạng LocalDateTime thành chuỗi
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedPayDate = payment.getVnp_PayDate().format(dateFormatter);

        // Tạo nội dung HTML cho phần thân email với CSS inline và bảng
        StringBuilder body = new StringBuilder();
        body.append("<html><body style='font-family: Arial, sans-serif; margin: 20px;'>")
                .append("<h2 style='color: #4CAF50;'>Hóa đơn của: " + payment.getName() + "</h2>")
                .append("<p>Đây là hóa đơn cho đơn hàng # <strong>" + payment.getVnp_TxnRef() + "</strong>.</p>")
                .append("<table style='width: 100%; border-collapse: collapse;'>")
                .append("<tr>")
                .append("<td style='border: 1px solid #ddd; padding: 8px; width: 50%;'><strong>Loại dịch vụ:</strong></td>")
                .append("<td style='border: 1px solid #ddd; padding: 8px; width: 50%;'>" + payment.getOrderType() + "</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style='border: 1px solid #ddd; padding: 8px;'><strong>Giá trị thanh toán:</strong></td>")
                .append("<td style='border: 1px solid #ddd; padding: 8px;'>" + formattedAmount + " VNĐ</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style='border: 1px solid #ddd; padding: 8px;'><strong>Ngày tạo:</strong></td>")
                .append("<td style='border: 1px solid #ddd; padding: 8px;'>" + formattedPayDate + "</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<td style='border: 1px solid #ddd; padding: 8px;'><strong>Email:</strong></td>")
                .append("<td style='border: 1px solid #ddd; padding: 8px;'>" + payment.getEmail() + "</td>")
                .append("</tr>")
                .append("</table>")
                .append("<hr style='border: 1px solid #4CAF50;'>")
                .append("<footer style='font-size: 12px; color: #777;'>")
                .append("Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!")
                .append("</footer>")
                .append("</body></html>");

        // Gửi email
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("your-email@example.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body.toString(), true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Error sending invoice email: " + e.getMessage());
        }
    }

    public void sendResetPasswordEmail(String email,String resetPasswordLink) {
        String messageBody = "Click the link to reset your password: <a href=\"" + resetPasswordLink + "\">Click here</a>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, true); // true = multipart
            helper.setSubject("Reset Your Password");
            helper.setTo(email);
            helper.setText(messageBody, true); // true = isHtml

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
