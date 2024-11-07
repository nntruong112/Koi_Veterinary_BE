package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.news.NewsCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.news.NewsUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.NewsResponse;
import com.KoiHealthService.Koi.demo.entity.News;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.NewsMapper;
import com.KoiHealthService.Koi.demo.repository.NewsRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NewsMapper newsMapper;

    private NewsService newsService;

    private NewsCreationRequest newsCreationRequest;
    private NewsUpdateRequest newsUpdateRequest;
    private User author;
    private News news;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        newsService = new NewsService(newsRepository, newsMapper, userRepository);

        author = new User();
        author.setUserId("1");
        author.setUsername("admin");

        news = new News();
        news.setNewsId("1");
        news.setTitle("Test News");
        news.setNewsContent("Test content");
        news.setAuthor(author);

        newsCreationRequest = new NewsCreationRequest();
        newsCreationRequest.setTitle("Test News");
        newsCreationRequest.setNewsContent("Test content");
        newsCreationRequest.setAuthorId("1");

        newsUpdateRequest = new NewsUpdateRequest();
        newsUpdateRequest.setTitle("Updated Test News");
        newsUpdateRequest.setNewsContent("Updated content");
        newsUpdateRequest.setAuthorId("1");
    }

    @Test
    void createNews_Success() {
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(author));
        Mockito.when(newsRepository.save(ArgumentMatchers.any(News.class))).thenReturn(news);

        News createdNews = newsService.createNews(newsCreationRequest);

        assertNotNull(createdNews, "Created news should not be null");
        assertEquals("Test News", createdNews.getTitle(), "News title should match");
        assertEquals("Test content", createdNews.getNewsContent(), "News content should match");
        assertEquals("1", createdNews.getAuthor().getUserId(), "Author ID should match");
    }

    @Test
    void createNews_UserNotFound() {Mockito.when(userRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(AnotherException.class, () -> {
            newsService.createNews(newsCreationRequest);
        });

        assertEquals("No veterinarian found", exception.getMessage(), "Error message should match");
    }

    @Test
    void getAllNews_Success() {
        Mockito.when(newsRepository.findAll()).thenReturn(List.of(news));

        List<News> newsList = newsService.getAllNews();

        assertNotNull(newsList, "News list should not be null");
        assertFalse(newsList.isEmpty(), "News list should not be empty");
        assertEquals(1, newsList.size(), "News list should contain exactly 1 item");
        assertEquals("Test News", newsList.get(0).getTitle(), "First news title should match");
    }

    @Test
    void getNewsById_Success() {
        Mockito.when(newsRepository.findById("1")).thenReturn(Optional.of(news));

        News fetchedNews = newsService.getNewsById("1");

        assertNotNull(fetchedNews, "Fetched news should not be null");
        assertEquals("Test News", fetchedNews.getTitle(), "Fetched news title should match");
    }

    @Test
    void getNewsById_NotFound() {
        Mockito.when(newsRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(AnotherException.class, () -> {
            newsService.getNewsById("1");
        });

        assertEquals("No news found", exception.getMessage(), "Error message should match");
    }

//    @Test
//    void updateNews_Success() {
//        Mockito.when(newsRepository.findById("1")).thenReturn(Optional.of(news));
//        Mockito.doNothing().when(newsMapper).updateNewsFromRequest(ArgumentMatchers.any(NewsUpdateRequest.class), ArgumentMatchers.any(News.class));
//        Mockito.when(newsRepository.save(ArgumentMatchers.any(News.class))).thenReturn(news);
//
//        NewsResponse updatedNewsResponse = newsService.updateNews("1", newsUpdateRequest);
//
//        assertNotNull(updatedNewsResponse, "Updated news response should not be null");
//        assertEquals("Updated Test News", updatedNewsResponse.getTitle(), "Updated title should match");
//        assertEquals("Updated content", updatedNewsResponse.getNewsContent(), "Updated content should match");
//    }
//
//    @Test
//    void updateNews_NotFound() {
//        Mockito.when(newsRepository.findById("1")).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(AnotherException.class, () -> {
//            newsService.updateNews("1", newsUpdateRequest);
//        });
//
//        assertEquals("No news found", exception.getMessage(), "Error message should match");
//    }

    @Test
    void deleteNews_Success() {
        Mockito.doNothing().when(newsRepository).deleteById("1");

        assertDoesNotThrow(() -> {
            newsService.deleteNews("1");
        });Mockito.verify(newsRepository, Mockito.times(1)).deleteById("1");
    }
}