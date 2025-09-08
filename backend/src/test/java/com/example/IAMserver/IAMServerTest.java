package com.example.IAMserver;

import com.example.IAMserver.dto.UserRegistrationRequest;
import com.example.IAMserver.user.UserEntityDetailsService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class IAMServerTest {

    @Autowired
    private UserEntityDetailsService userEntityDetailsService;

    @Test
    void contextLoads() {
    }

    @Test
    void testRaceCondition() throws InterruptedException {
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    userEntityDetailsService.registerUser(new UserRegistrationRequest("abc@example.com", "password", "abc"));
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " -> " + e.getClass().getSimpleName());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);


    }


}
