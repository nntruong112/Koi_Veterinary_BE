package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,String> {

}
