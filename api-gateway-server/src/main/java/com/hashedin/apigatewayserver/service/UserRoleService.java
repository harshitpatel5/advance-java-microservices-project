package com.hashedin.apigatewayserver.service;

import com.hashedin.apigatewayserver.exception.CustomException;
import com.hashedin.apigatewayserver.models.MyUser;
import com.hashedin.apigatewayserver.models.Role;
import com.hashedin.apigatewayserver.models.UserRole;
import com.hashedin.apigatewayserver.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    UserRoleRepository repository;

    @Autowired
    RoleService roleService;


    public UserRole save(UserRole userRole) throws CustomException {
        UserRole createdUserRole = repository.save(userRole);
        if(createdUserRole == null){
            throw new CustomException("Unable to create UserRole");
        }
        return createdUserRole;
    }

    public UserRole findByUserId(Long id) throws CustomException {
        UserRole userRole = repository.findByUserId(id);
        if(userRole == null){
            throw new CustomException("No UserRole found");
        }
        return userRole;
    }

    public String deleteUserRole(UserRole userRole) throws CustomException {
        try{
            if(userRole != null)
                repository.delete(userRole);
        } catch (Exception e){
            throw new CustomException("Unable to delete UserRole");
        }
        return "Deleted UserRole";
    }

    public Role updateUserRole(MyUser myUser, String name) throws CustomException {
        UserRole userRoleToBeDeleted = repository.findByUserId(myUser.getId());
        repository.delete(userRoleToBeDeleted);
        Role role = roleService.findByName(name);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(myUser.getId());
        repository.save(userRole);
        return role;
    }
}
