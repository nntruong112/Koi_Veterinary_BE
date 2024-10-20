package com.KoiHealthService.Koi.demo.config;

import com.KoiHealthService.Koi.demo.entity.Item;
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
        String subject = "Hóa đơn #" + payment.getPaymentId();

        // Create HTML content for the email body
        StringBuilder body = new StringBuilder();
        body.append("<html><body>")
            .append("<h2>Hóa đơn của : " + payment.getName()+ "</h2>")
            .append("<p>Đây là hóa đơn cho đơn hàng # " + payment.getPaymentId() + ".</p>")
            .append("<table style='border-collapse: collapse; width: 100%;'>")
            .append("<tr style='background-color: #f2f2f2;'>")
            .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Mặt hàng</th>")
            .append("<th style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>Giá</th>")
            .append("</tr>");

        for (Item item : payment.getItems()) {
            body.append("<tr>")
                .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                .append(item.getCreateDate()) // Assuming item has a getName() method
                .append("</td>")
                .append("<td style='border: 1px solid #dddddd; text-align: left; padding: 8px;'>")
                .append(item.getExpireDate()) // Assuming item has a getPrice() method
                .append("</td>")
                .append("</tr>");
        }

        body.append("</table></body></html>");

        // Send email
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart
            helper.setFrom("your-email@example.com"); // Sender's email
            helper.setTo(to); // Recipient's email
            helper.setSubject(subject);
            helper.setText(body.toString(), true); // true indicates HTML content

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception, e.g., log the error
            System.err.println("Error sending invoice email: " + e.getMessage());
        }
    }
}
