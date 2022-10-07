package com.hashedin.apigatewayserver.service;

import com.hashedin.apigatewayserver.exception.CustomException;
import com.hashedin.apigatewayserver.models.MyUser;
import com.hashedin.apigatewayserver.models.Role;
import com.hashedin.apigatewayserver.models.UserResponseModel;
import com.hashedin.apigatewayserver.models.UserRole;
import com.hashedin.apigatewayserver.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MyUserService  {

    @Autowired
    MyUserRepository repository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    private MyUser findByUsername(String username){
        MyUser myUser = repository.findByUsername(username);
        return myUser;
    }

    public UserResponseModel createUser(UserResponseModel userResponseModel) throws CustomException {
        Role role = roleService.findByName(userResponseModel.getRole().getName());
        if(role == null){
            throw new CustomException("Unable to find ROLE by name: "+role.getName());
        }
        MyUser user = repository.save(userResponseModel.getUser());
        if(user == null){
            throw new CustomException("Unable to create USER");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);
        userResponseModel.setUser(user);
        userResponseModel.setRole(role);
        return userResponseModel;
    }

    public void deleteUserById(Long id) throws CustomException {
        UserRole userRole = userRoleService.findByUserId(id);
        userRoleService.deleteUserRole(userRole);
        Optional<MyUser> userOptional = repository.findById(id);
        if(!userOptional.isPresent()){
            throw new CustomException("Unable to find user to delete");
        }
        repository.delete(userOptional.get());
    }

    public List<UserResponseModel> fetchAllUsers() throws CustomException {
        List<MyUser> myUsers = repository.findAll();
        List<UserResponseModel> userResponseModels = new ArrayList<>();
        for(MyUser user: myUsers){
            UserResponseModel userResponseModel = new UserResponseModel();
            userResponseModel.setUser(user);
            UserRole userRole = userRoleService.findByUserId(user.getId());
            Role role = roleService.findById(userRole.getRoleId());
            userResponseModel.setRole(role);
            userResponseModels.add(userResponseModel);
        }
        return userResponseModels;
    }

    public UserResponseModel updateUser(UserResponseModel userResponseModel) throws CustomException {
        MyUser myUser = repository.findByUsername(userResponseModel.getUser().getUsername());
        myUser.setPassword(userResponseModel.getUser().getPassword());
        myUser = repository.save(myUser);
        Role role = userRoleService.updateUserRole(myUser, userResponseModel.getRole().getName());
        userResponseModel.setUser(myUser);
        userResponseModel.setRole(role);
        return userResponseModel;
    }
}
