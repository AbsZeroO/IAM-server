package com.example.IAMserver.user;

import com.example.IAMserver.authorities.RoleService;
import com.example.IAMserver.dto.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEntityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Transactional(readOnly = true)
    @Override
    public UserEntity loadUserByUsername(String username) throws UserNotFound {
        return userRepository.findByUsername(username)
                .filter(user -> !user.getAuthorities().isEmpty())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public void registerUser(UserRegistrationRequest userRegistrationRequest) throws UserAlreadyExistsException {
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

    @Transactional
    public void deleteUserById(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Attempt to delete user with id '{}' that doesn't exist", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });

        try {
            userRepository.delete(userEntity);
            log.info("User with id '{}' deleted successfully", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to delete user with id '{}'", id, e);
            throw e;
        }
    }


}
