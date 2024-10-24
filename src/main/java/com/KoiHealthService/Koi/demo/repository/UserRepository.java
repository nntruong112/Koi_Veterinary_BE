package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    List<User> findByRoles(String roles);
    Optional<User> findByEmail(String email);
}
