package com.KoiHealthService.Koi.demo.config;

import com.KoiHealthService.Koi.demo.Enum.Role;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
@Builder
public class ApplicationInitConfig {

    @NonNull
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
           if(userRepository.findByUsername("admin").isEmpty()){
               var roles = new HashSet<String>();
               roles.add(Role.ADMIN.name());
               User user = User.builder()
                       .username("admin")
                       .password(passwordEncoder.encode("12345"))
                       .roles(roles)
                       .build();

               userRepository.save(user);
               log.warn("default user has been with default password : 12345");
           }
        };
    }
}
