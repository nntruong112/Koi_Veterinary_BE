package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.fish.FishCreationRequest;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.mapper.FishMapper;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FishServiceTest {

    @Mock
    private FishRepository fishRepository;

    @InjectMocks
    private FishService fishService;
    private FishCreationRequest fishCreateRequest;
    private Fish fish;
    @Mock
    private FishMapper fishMapper;

    @BeforeEach
    void setUp() {
        fishCreateRequest = FishCreationRequest.builder()
                .fishId("0c544ee6-ca0e-430c-a942-6387bd22dba6")
                .age(3)
                .species("orange")
                .build();
        fish = Fish.builder()
                .fishId(fishCreateRequest.getFishId())
                .age(fishCreateRequest.getAge())
                .species(fishCreateRequest.getSpecies())
                .build();
    }

    @Test
    void testCreateKoiSuccess() {
        // Mock fishMapper to return a Fish object when converting the request
        when(fishMapper.toFish(any(FishCreationRequest.class))).thenReturn(fish);

        // Mock fishRepository save behavior
        when(fishRepository.save(any(Fish.class))).thenReturn(fish);

        // Perform the service method
        Fish createdFish = fishService.createFish(fishCreateRequest);

        // Verify interactions and assert result
        assertNotNull(createdFish);
        assertEquals(3, createdFish.getAge());
        assertEquals("orange", createdFish.getSpecies());
        verify(fishMapper, times(1)).toFish(any(FishCreationRequest.class));
        verify(fishRepository, times(1)).save(any(Fish.class));
    }

    @Test
    void testCreateKoiNotFound() {
        // Mock fishRepository behavior to return empty
        when(fishRepository.findById(fishCreateRequest.getFishId())).thenReturn(Optional.empty());

        // Perform the service method and expect an exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fishService.getByFish(fishCreateRequest.getFishId());
        });

        // Verify the exception message
        String expectedMessage = "Fish not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Verify findById interaction
        verify(fishRepository, times(1)).findById(fishCreateRequest.getFishId());
    }
}