package com.example.IAMserver.authoritie;

import com.example.IAMserver.authoritie.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getDefaultRole(){
        return roleRepository.getDefaultRole().orElseThrow(()->new EntityNotFoundException("Role Not Found"));
    }

}
