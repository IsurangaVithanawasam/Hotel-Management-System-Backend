package com.isurangavithanawasam.hotelmanagementsystem.controller;

import com.isurangavithanawasam.hotelmanagementsystem.dto.LoginRequest;
import com.isurangavithanawasam.hotelmanagementsystem.dto.Response;
import com.isurangavithanawasam.hotelmanagementsystem.entity.User;
import com.isurangavithanawasam.hotelmanagementsystem.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
