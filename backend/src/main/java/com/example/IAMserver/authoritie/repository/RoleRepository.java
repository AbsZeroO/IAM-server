package com.example.IAMserver.authoritie.repository;

import com.example.IAMserver.authoritie.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleName(String roleName);

    @Query("SELECT r FROM RoleEntity r WHERE r.roleName = 'ROLE_USER'")
    Optional<RoleEntity> getDefaultRole();

}
