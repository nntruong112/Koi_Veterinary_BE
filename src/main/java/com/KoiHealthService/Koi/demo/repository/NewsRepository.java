package com.KoiHealthService.Koi.demo.repository;

import com.KoiHealthService.Koi.demo.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, String> {
}
