package com.example.IAMserver.auth;

import com.example.IAMserver.auth.dto.UserLoginRequest;
import com.example.IAMserver.authoritie.RoleService;
import com.example.IAMserver.auth.dto.UserRegistrationRequest;
import com.example.IAMserver.user.*;
import com.example.IAMserver.utilJWT.JWTService;
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
    private final JWTService jwtService;

    @Transactional
    public void registerLocalUser(UserRegistrationRequest userRegistrationRequest) throws UserAlreadyExistsException {
        try {
            userRepository.save(
                    UserEntity.builder()
                            .email(userRegistrationRequest.email())
                            .password(passwordEncoder.encode(userRegistrationRequest.password()))
                            .username(userRegistrationRequest.username())
                            .role(roleService.getDefaultRole())
                            .outsideAuthProvider(OutsideAuthProvider.LOCAL)
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            log.warn("Attempt to register user with existing email '{}' or username '{}'",
                    userRegistrationRequest.email(), userRegistrationRequest.username(), e);

            throw new UserAlreadyExistsException(
                    String.format("User with email '%s' or username '%s' already exists",
                            userRegistrationRequest.email(), userRegistrationRequest.username())
            );
        }
    }


    @Transactional(readOnly = true)
    public String loginLocalUser(UserLoginRequest userLoginRequest) throws JOSEException {
        UserEntity user = userRepository.findByEmail(userLoginRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userLoginRequest.email()));

        if (!passwordEncoder.matches(userLoginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtService.generateToken(user);
    }

}
