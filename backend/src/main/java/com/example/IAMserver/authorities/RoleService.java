package com.example.IAMserver.authorities;

import com.example.IAMserver.authorities.repository.RoleRepository;
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
