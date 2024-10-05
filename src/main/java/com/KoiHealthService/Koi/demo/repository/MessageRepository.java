package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
