//package com.hashedin.apigatewayserver.servicetests;
//
//import com.hashedin.apigatewayserver.exception.CustomException;
//import com.hashedin.apigatewayserver.models.MyUser;
//import com.hashedin.apigatewayserver.models.Role;
//import com.hashedin.apigatewayserver.models.UserRole;
//import com.hashedin.apigatewayserver.repository.MyUserRepository;
//import com.hashedin.apigatewayserver.service.MyUserService;
//import com.hashedin.apigatewayserver.service.RoleService;
//import com.hashedin.apigatewayserver.service.UserRoleService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserServiceTests {
//
//    @Autowired
//    private MyUserService userService;
//
//    @MockBean
//    private MyUserRepository myUserRepository;
//
//    @MockBean
//    UserRoleService userRoleService;
//
//    @MockBean
//    RoleService roleService;
//
//    @Test
//    public void getAllUsersTest() throws CustomException {
//        List<MyUser> userList = new ArrayList<>();
//        userList.add(new MyUser(1l));
//        userList.add(new MyUser(1l));
//        UserRole userRole = new UserRole();
//        userRole.setId(1l);
//        userRole.setUserId(1l);
//        userRole.setRoleId(1l);
//        when(myUserRepository.findAll()).thenReturn(userList);
//        when(userRoleService.findByUserId(1l)).thenReturn(userRole);
//        when(roleService.findById(1l)).thenReturn(new Role());
//        assertEquals(2, userService.fetchAllUsers().size());
//    }
//}
