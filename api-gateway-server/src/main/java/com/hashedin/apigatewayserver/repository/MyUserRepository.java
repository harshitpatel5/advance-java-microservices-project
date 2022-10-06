package com.hashedin.apigatewayserver.repository;

import com.hashedin.apigatewayserver.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUsername(String username);
}
