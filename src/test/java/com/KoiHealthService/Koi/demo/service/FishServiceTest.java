package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.fish.FishCreationRequest;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Transactional
class FishServiceTest {
    @Autowired
    private FishRepository fishRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FishService fishService;  // The service containing the createFish method

    private User testUser;
    private FishCreationRequest fishRequest;

    @BeforeEach
    void setUp() {
        // Setup a test user and save it to the database
        testUser = new User();
        testUser.setFirstname("Test User");
        userRepository.save(testUser);

        // Set up the FishCreationRequest with required data
        fishRequest = new FishCreationRequest();
        fishRequest.setCustomerId(testUser.getUserId());  // Use saved test user ID
        fishRequest.setSpecies("Koi");
        fishRequest.setAge(2);
        fishRequest.setImage("koi-image.jpg");
        fishRequest.setWeight(2);
        fishRequest.setSize(20);
        fishRequest.setColor("Red");
        fishRequest.setGender("Female");
    }

    @Test
    void testCreateFishSuccess() {
        // Act
        Fish createdFish = fishService.createFish(fishRequest);

        // Assert
        assertNotNull(createdFish);
        assertEquals(fishRequest.getSpecies(), createdFish.getSpecies());
        assertEquals(fishRequest.getAge(), createdFish.getAge());
        assertEquals(fishRequest.getImage(), createdFish.getImage());
        assertEquals(fishRequest.getWeight(), createdFish.getWeight());
        assertEquals(fishRequest.getSize(), createdFish.getSize());
        assertEquals(fishRequest.getColor(), createdFish.getColor());
        assertEquals(fishRequest.getGender(), createdFish.getGender());
        assertEquals(testUser.getUserId(), createdFish.getCustomer().getUserId());
    }

    @Test
    void testCreateFishCustomerNotFound() {
        // Arrange
        fishRequest.setCustomerId("999L"); // Set a non-existent customer ID

        // Act & Assert
        Exception exception = assertThrows(AnotherException.class, () -> {
            fishService.createFish(fishRequest);
        });

        assertEquals(ErrorCode.NO_CUSTOMER_FOUND, ((AnotherException) exception).getErrorCode());
    }
}