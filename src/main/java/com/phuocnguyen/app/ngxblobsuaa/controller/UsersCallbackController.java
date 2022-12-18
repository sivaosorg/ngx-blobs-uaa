package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.phuocnguyen.app.ngxblobsuaa.service.UsersUaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ngxsivaos.variable.RoutesApiVariable.ROUTES_API_CALLBACK;

@RestController
@RequestMapping(value = ROUTES_API_CALLBACK)
public class UsersCallbackController {

    private final UsersUaaService usersUaaService;

    @Autowired
    public UsersCallbackController(
            UsersUaaService usersUaaService) {
        this.usersUaaService = usersUaaService;
    }

    @PostMapping("users")
    public @ResponseBody
    ResponseEntity<?> findUser(@RequestBody UsersFilter filter) {
        return new ResponseEntity<>(usersUaaService.findUserBy(filter), HttpStatus.OK);
    }
}
