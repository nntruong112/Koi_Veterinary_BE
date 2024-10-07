package com.KoiHealthService.Koi.demo.Storage;

import com.KoiHealthService.Koi.demo.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.KoiHealthService.Koi.demo")
public class UserStorage {
 private Map<String, User> verificationCodes = new HashMap<>();

    public void storeVerificationCode(String code, User user) {
        verificationCodes.put(code,user);
    }

    public User getUserByVerificationCode(String code) {
        return verificationCodes.get(code);
    }
    public void storeEmail(String email, User user){
        verificationCodes.put(email,user);
    }

    public User getUserEmail(String email){
        return verificationCodes.get(email);
    }

}
