package com.map.harass.login.api;

import com.map.harass.dto.LoginRequest;
import com.map.harass.dto.LoginResponse;
import com.map.harass.login.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j(topic = "Login_Controller")
@RequestMapping("/api/v1/login")
@CrossOrigin("*")

public class LoginController {
    @Autowired
    private UserServiceImpl usersServiceImpl;
    public LoginController() {
    }
    @PostMapping("/authenticate")
    public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) throws Exception {
        return usersServiceImpl.authenticate(loginRequest);

    }
}
