package com.KoiHealthService.Koi.demo.controller;


import com.KoiHealthService.Koi.demo.dto.request.news.NewsCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.news.NewsUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.NewsResponse;
import com.KoiHealthService.Koi.demo.entity.News;
import com.KoiHealthService.Koi.demo.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<News>> createNews(@RequestBody @Valid NewsCreationRequest request) {
        ApiResponse<News> apiResponse = new ApiResponse<>();
        apiResponse.setResult(newsService.createNews(request));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") String newsId) {
        News news = newsService.getNewsById(newsId);
        return ResponseEntity.ok(news);
    }

    @PutMapping("/{newsId}")
    public NewsResponse updateNews(
            @PathVariable("newsId") String newsId,
            @RequestBody NewsUpdateRequest request) {
        return newsService.updateNews(newsId, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable String id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

}
