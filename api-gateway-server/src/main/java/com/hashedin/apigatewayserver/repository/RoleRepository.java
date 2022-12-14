package com.hashedin.apigatewayserver.repository;

import com.hashedin.apigatewayserver.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
