package com.luka.sneksibetting.controllers;

import com.luka.sneksibetting.models.user.UserInfo;
import com.luka.sneksibetting.models.user.UserLoginCreds;
import com.luka.sneksibetting.models.user.UserRegisterCreds;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luka.sneksibetting.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfo> loginUser(@RequestBody UserLoginCreds creds) {
        Optional<UserInfo> userInfo = userService.loginUser(creds);

        return userInfo
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfo> registerUser(@RequestBody UserRegisterCreds creds) {
        return ResponseEntity.ok(userService.registerUser(creds));
    }
}
