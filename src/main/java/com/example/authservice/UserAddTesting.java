package com.example.authservice;

import com.example.authservice.authorities.PermissionEntity;
import com.example.authservice.authorities.RoleEntity;
import com.example.authservice.authorities.repository.PermissionRepository;
import com.example.authservice.authorities.repository.RoleRepository;
import com.example.authservice.user.OutsideAuthProvider;
import com.example.authservice.user.UserEntity;
import com.example.authservice.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class UserAddTesting {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       PermissionRepository permissionRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {

            PermissionEntity readPermission = permissionRepository
                    .findByPermissionName("READ")
                    .orElseGet(() -> {
                        PermissionEntity newPermission = new PermissionEntity();
                        newPermission.setPermissionName("READ");
                        return permissionRepository.save(newPermission);
                    });

            RoleEntity adminRole = roleRepository
                    .findByRoleName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        RoleEntity role = new RoleEntity();
                        role.setRoleName("ROLE_ADMIN");
                        role.setPermissions(Set.of(readPermission));
                        return roleRepository.save(role);
                    });

            if (userRepository.findByUsername("user").isEmpty()) {
                UserEntity user = UserEntity.builder()
                        .email("user@example.com")
                        .username("user")
                        .password(passwordEncoder.encode("password"))
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .outsideAuthProvider(OutsideAuthProvider.LOCAL)
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(user);
            }
        };
    }


}
