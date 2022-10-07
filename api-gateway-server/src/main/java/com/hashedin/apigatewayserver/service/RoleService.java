package com.hashedin.apigatewayserver.service;

import com.hashedin.apigatewayserver.exception.CustomException;
import com.hashedin.apigatewayserver.models.Role;
import com.hashedin.apigatewayserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository repository;

    public Role createRoleByName(String roleName) throws CustomException {
        Role existingRole = repository.findByName(roleName);
        if(existingRole != null){
            throw new CustomException("role already exist by name: "+roleName);
        }
        Role role = new Role();
        role.setName(roleName);
        role = repository.save(role);
        if(role == null){
            throw new CustomException("unable to create Role");
        }
        return role;
    }

    public Role findByName(String roleName) throws CustomException {
        Role role = repository.findByName(roleName);
        if(role == null){
            throw new CustomException("No ROLE found by name: "+roleName);
        }
        return role;
    }

    public String deleteRoleByName(String roleName) throws CustomException {
        try {
            Role role = repository.findByName(roleName);
            repository.delete(role);
        } catch (Exception e){
            throw new CustomException("Unable to delete exception by name: "+roleName);
        }
        return "Successfully deleted Role";
    }

    public Role findById(Long roleId) throws CustomException {
        Optional<Role> roleOptional = repository.findById(roleId);
        if(!roleOptional.isPresent()){
            throw new CustomException("No ROLE present");
        }
        return roleOptional.get();
    }

    public List<Role> findAll() {
        List<Role> roles = repository.findAll();
        return roles;
    }
}
