package com.example.IAMserver;

import com.example.IAMserver.authorities.PermissionEntity;
import com.example.IAMserver.authorities.RoleEntity;
import com.example.IAMserver.authorities.repository.PermissionRepository;
import com.example.IAMserver.authorities.repository.RoleRepository;
import com.example.IAMserver.user.OutsideAuthProvider;
import com.example.IAMserver.user.UserEntity;
import com.example.IAMserver.user.UserRepository;
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

            RoleEntity userRole = roleRepository
                    .findByRoleName("ROLE_USER")
                    .orElseGet(() -> {
                        RoleEntity role = new RoleEntity();
                        role.setRoleName("ROLE_USER");
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
