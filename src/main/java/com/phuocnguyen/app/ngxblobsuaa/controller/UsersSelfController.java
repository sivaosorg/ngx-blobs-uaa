package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobssrv.controllers.NgxBaseController;
import com.sivaos.Model.Response.SIVAResponseDTO;
import com.sivaos.Utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import static com.ngxsivaos.variable.RoutesApiVariable.ROUTES_API_USER_SELF;

@RestController
@RequestMapping(value = ROUTES_API_USER_SELF)
public class UsersSelfController extends NgxBaseController {

    private static final Logger logger = LoggerFactory.getLogger(UsersSelfController.class);

    @Value("${spring.profiles.active}")
    private String profileActive;

    @PostConstruct
    public void init() {
        this.setActiveSessionInfo(true);
        this.setActiveUserPrincipal(false);
        this.setProfile(profileActive);
    }

    @GetMapping(value = "/info")
    public @ResponseBody
    ResponseEntity<?> findSelfInfo() {
        logger.info("Json auth request: {}", LoggerUtils.toJson(authSecuritiesContextRequest));
        logger.info("Users request: {}", LoggerUtils.toJson(userRequest));
        return new ResponseEntity<>(SIVAResponseDTO.buildSIVAResponse(userRequest), HttpStatus.OK);
    }
}
