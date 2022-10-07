package com.hashedin.apigatewayserver.controller;


import com.hashedin.apigatewayserver.exception.CustomException;
import com.hashedin.apigatewayserver.models.CurrencyExchange;
import com.hashedin.apigatewayserver.models.Role;
import com.hashedin.apigatewayserver.models.UserResponseModel;
import com.hashedin.apigatewayserver.models.UserRole;
import com.hashedin.apigatewayserver.proxy.CurrencyExchangeProxy;
import com.hashedin.apigatewayserver.service.MyUserService;
import com.hashedin.apigatewayserver.service.RoleService;
import com.hashedin.apigatewayserver.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    MyUserService userService;

    @Autowired
    CurrencyExchangeProxy proxy;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @GetMapping("/test")
    public String test(){
        return "user okay";
    }

    @GetMapping("/list-all-users")
    public List<UserResponseModel> getAllUsers() throws CustomException {
        return userService.fetchAllUsers();
    }

    @PostMapping("/create-user")
    public UserResponseModel createUser(@RequestBody UserResponseModel userResponseModel) throws CustomException {
        userResponseModel = userService.createUser(userResponseModel);
        return userResponseModel;
    }

    @PutMapping("/update-user")
    public UserResponseModel updateUser(@RequestBody UserResponseModel userResponseModel) throws CustomException {
        userResponseModel = userService.updateUser(userResponseModel);
        return userResponseModel;
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) throws CustomException {
        userService.deleteUserById(id);
        return "Successfully deleted User";
    }

    @PostMapping("/create-exchange-rate-by-user/{userId}")
    public CurrencyExchange createCurrencyExchange(@RequestBody CurrencyExchange currencyExchange, @PathVariable Long userId) throws CustomException {
        UserRole userRoleMapping = userRoleService.findByUserId(userId);
        Role userRole = roleService.findById(userRoleMapping.getRoleId());
        if(!"ADMIN".equalsIgnoreCase(userRole.getName())){
            throw new CustomException("only ADMIN can create ExchangeRate!");
        }
        try {
            CurrencyExchange existingCurrencyExchange = proxy.retrieveExchangeValue(currencyExchange.getFrom(), currencyExchange.getTo());
            if(existingCurrencyExchange != null){
                throw new CustomException("This EchangeRate already exists");
            }
        } catch (Exception e){
            System.out.println("CurrencyExchange does not exist, let's create one");
        }

        CurrencyExchange exchangeRate = proxy.createExchangeRate(currencyExchange);
        return exchangeRate;
    }

    @PutMapping("/update-exchange-rate-by-user/{userId}")
    public CurrencyExchange updateCurrencyExchange(@RequestBody CurrencyExchange currencyExchange, @PathVariable Long userId) throws CustomException {
        UserRole userRoleMapping = userRoleService.findByUserId(userId);
        Role userRole = roleService.findById(userRoleMapping.getRoleId());
        if(!"ADMIN".equalsIgnoreCase(userRole.getName())){
            throw new CustomException("only ADMIN can update ExchangeRate!");
        }
        try {
            CurrencyExchange existingCurrencyExchange = proxy.retrieveExchangeValue(currencyExchange.getFrom(), currencyExchange.getTo());
            if(existingCurrencyExchange != null){
                existingCurrencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple());
                CurrencyExchange exchangeRate = proxy.createExchangeRate(existingCurrencyExchange);
                return exchangeRate;
            }
        } catch (Exception e){
            System.out.println("CurrencyExchange does not exist, let's create one");
        }

        CurrencyExchange exchangeRate = proxy.createExchangeRate(currencyExchange);
        return exchangeRate;
    }

    @DeleteMapping("/delete-exchange-rate-by-user/{userId}")
    public String deleteExchangerateByUser(@RequestBody CurrencyExchange currencyExchange, @PathVariable Long userId) throws CustomException {
        UserRole userRoleMapping = userRoleService.findByUserId(userId);
        Role userRole = roleService.findById(userRoleMapping.getRoleId());
        if(!"ADMIN".equalsIgnoreCase(userRole.getName())){
            throw new CustomException("only ADMIN can update ExchangeRate!");
        }
        try {
            proxy.deleteByFromAndTo(currencyExchange.getFrom(), currencyExchange.getTo());
        } catch (Exception e){
            throw new CustomException("Unable to delete CurrencyExchange");
        }
        return "Successfully deleted CurrencyExchange";
    }

    @GetMapping("/list-exchange-rate-by-user/{userId}")
    public List<CurrencyExchange> listCurrencyExchangeByUser(@PathVariable Long userId) throws CustomException {
        UserRole userRoleMapping = userRoleService.findByUserId(userId);
        Role userRole = roleService.findById(userRoleMapping.getRoleId());
        if(!"ADMIN".equalsIgnoreCase(userRole.getName())){
            throw new CustomException("only ADMIN can update ExchangeRate!");
        }
        return proxy.getAllCurrencyExchange();
    }
}
