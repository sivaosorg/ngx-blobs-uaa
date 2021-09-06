package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobsuaa.service.UserService;
import com.sivaos.Model.Request.Original.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sivaos.Variables.RoutesVariable.ROUTES_API_USER;

@RestController
@RequestMapping(value = ROUTES_API_USER)
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(
            UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public @ResponseBody
    ResponseEntity<?> login(@RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(userService.login(signInRequest), HttpStatus.OK);
    }
}
