package com.hashedin.apigatewayserver.controller;

import com.hashedin.apigatewayserver.exception.CustomException;
import com.hashedin.apigatewayserver.models.Role;
import com.hashedin.apigatewayserver.models.UserRole;
import com.hashedin.apigatewayserver.service.RoleService;
import com.hashedin.apigatewayserver.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/create-by-user/{userId}")
    public Role createRole(@RequestBody Role role, @PathVariable Long userId) throws CustomException {
        UserRole userRoleMapping = userRoleService.findByUserId(userId);
        Role userRole = roleService.findById(userRoleMapping.getRoleId());
        if(!"ADMIN".equalsIgnoreCase(userRole.getName())){
            throw new CustomException("only ADMIN can create role!");
        }
        Role roleByName = roleService.createRoleByName(role.getName());
        return roleByName;
    }

    @GetMapping("/get-all")
    public List<Role> getAllRoles(){
        List<Role> roles = roleService.findAll();
        return roles;
    }

    @DeleteMapping("/delete-by-user/{userId}")
    public String deleteRole(@RequestBody Role role, @PathVariable Long userId) throws CustomException {
        if("ADMIN".equalsIgnoreCase(role.getName())){
            throw new CustomException("cannot delete role ADMIN");
        }
        UserRole userRoleMapping = userRoleService.findByUserId(userId);
        Role userRole = roleService.findById(userRoleMapping.getRoleId());
        if(!"ADMIN".equalsIgnoreCase(userRole.getName())){
            throw new CustomException("only ADMIN can create role!");
        }
        roleService.deleteRoleByName(role.getName());
        return "Successfully deleted ROLE";
    }
}
