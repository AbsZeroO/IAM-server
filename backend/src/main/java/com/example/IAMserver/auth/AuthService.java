package com.example.IAMserver.auth;

import com.example.IAMserver.auth.dto.UserLoginRequest;
import com.example.IAMserver.authoritie.RoleService;
import com.example.IAMserver.auth.dto.UserRegistrationRequest;
import com.example.IAMserver.user.*;
import com.example.IAMserver.JWT.JWTGenerateService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JWTGenerateService jwtService;

    @Transactional
    public void registerLocalUser(UserRegistrationRequest request) throws UserAlreadyExistsException {
        log.info("Attempting to register new user with email='{}' and username='{}'",
                request.email(), request.username());

        if (userRepository.findByEmail(request.email()).isPresent()) {
            log.warn("User with email='{}' already exists", request.email());
            throw new UserAlreadyExistsException(
                    String.format("User with email '%s' already exists", request.email())
            );
        }

        if (userRepository.findByUsername(request.username()).isPresent()) {
            log.warn("User with username='{}' already exists", request.username());
            throw new UserAlreadyExistsException(
                    String.format("User with username '%s' already exists", request.username())
            );
        }

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .username(request.username())
                .role(roleService.getDefaultRole())
                .outsideAuthProvider(OutsideAuthProvider.LOCAL)
                .build();

        try {

            log.debug("Saving new user entity: {}", user);
            userRepository.saveAndFlush(user);
            log.info("Successfully registered user with email='{}'", request.email());

        } catch (DataIntegrityViolationException e) {
            log.error("Unique constraint violation while registering user: email='{}', username='{}'",
                    request.email(), request.username(), e);

            throw new UserAlreadyExistsException(
                    String.format("User with email '%s' or username '%s' already exists",
                            request.email(), request.username())
            );
        }
    }

    @Transactional(readOnly = true)
    public String loginLocalUser(UserLoginRequest userLoginRequest) throws JOSEException {
        log.info("Login attempt for email='{}'", userLoginRequest.email());

        UserEntity user = userRepository.findByEmail(userLoginRequest.email())
                .orElseThrow(() -> {
                    log.warn("Login failed: no user found with email='{}'", userLoginRequest.email());
                    return new UserNotFoundException("User not found with email: " + userLoginRequest.email());
                });

        if (!passwordEncoder.matches(userLoginRequest.password(), user.getPassword())) {
            log.warn("Login failed for email='{}': invalid password", userLoginRequest.email());
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user);
        log.info("Login successful for email='{}'. Token generated.", userLoginRequest.email());

        return token;
    }
}
