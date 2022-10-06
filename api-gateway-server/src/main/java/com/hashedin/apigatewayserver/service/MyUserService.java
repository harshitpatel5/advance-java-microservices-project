package com.hashedin.apigatewayserver.service;

import com.hashedin.apigatewayserver.models.MyUser;
import com.hashedin.apigatewayserver.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import java.util.ArrayList;

@Service
public class MyUserService  {

    @Autowired
    MyUserRepository repository;

    private MyUser findByUsername(String username){
        MyUser myUser = repository.findByUsername(username);
        return myUser;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        MyUser myUser = repository.findByUsername(username);
//        User user = new User("harshit", "Qwerty@123", new ArrayList<>());
//        return user;
//    }
}
