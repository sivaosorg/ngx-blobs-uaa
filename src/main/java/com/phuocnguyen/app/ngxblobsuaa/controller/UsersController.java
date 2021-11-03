package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobssrv.controllers.NgxBaseRoutesController;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersBaseService;
import com.sivaos.config.propertiesConfig.AuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ngxsivaos.variable.RoutesApiVariable.ROUTES_API_USER;

@RestController
@RequestMapping(value = ROUTES_API_USER)
public class UsersController extends NgxBaseRoutesController {

    @Autowired
    public UsersController(
            NgxUsersBaseService ngxUsersBaseService,
            AuthenticationProperties authenticationProperties) {
        super(ngxUsersBaseService, authenticationProperties);
    }

}
