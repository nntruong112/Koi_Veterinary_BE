package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.NewsCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.NewsUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.NewsResponse;
import com.KoiHealthService.Koi.demo.entity.News;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.NewsMapper;
import com.KoiHealthService.Koi.demo.repository.NewsRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private final UserRepository userRepository;

    private User author;

    private News news;

    public News createNews(NewsCreationRequest request) {
        author = userRepository.findById(request.getAuthorId())
                .orElseThrow(()-> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));

        news = News.builder()
                .author(author)
                .title(request.getTitle())
                .newsContent(request.getNewsContent())
                .build();

        return newsRepository.save(news);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNewsById(String id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_NEWS_FOUND));
    }

    public NewsResponse updateNews(String id, NewsUpdateRequest request) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id " + id));

        newsMapper.updateNewsFromRequest(request, existingNews);

        // Update author if authorId is provided
        userRepository.findById(request.getAuthorId())
                .ifPresent(existingNews::setAuthor);

        News updatedNews = newsRepository.save(existingNews);
        return newsMapper.toNewsResponse(updatedNews);
    }

    public void deleteNews(String id) {
        newsRepository.deleteById(id);
    }
}
