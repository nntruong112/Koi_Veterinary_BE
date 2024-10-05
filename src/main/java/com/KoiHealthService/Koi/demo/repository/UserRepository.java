package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByUserId(String userId);
    User findByEmailAndVerificationCode(String email, String verificationCode);

}
