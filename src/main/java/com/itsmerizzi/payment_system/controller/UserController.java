package com.itsmerizzi.payment_system.controller;

import com.itsmerizzi.payment_system.dto.UserRequest;
import com.itsmerizzi.payment_system.dto.UserResponse;
import com.itsmerizzi.payment_system.entity.User;
import com.itsmerizzi.payment_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
        User user = userRequest.toModel();
        UserResponse savedUser = userService.registerUser(user);
        return ResponseEntity.ok().body(savedUser);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "Verified";
        }
        return "Not verified";
    }

}
