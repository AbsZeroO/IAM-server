package com.example.authenticationservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileChecker implements CommandLineRunner {

    private final Environment env;

    public ProfileChecker(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        String[] profiles = env.getActiveProfiles();
        System.out.println("Active Spring profiles: " + (profiles.length > 0 ? String.join(", ", profiles) : "default"));
    }
}
