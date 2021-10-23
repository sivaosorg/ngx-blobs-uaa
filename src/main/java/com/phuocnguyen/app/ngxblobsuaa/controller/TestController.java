package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobssrv.controllers.NgxBaseController;
import com.sivaos.Model.Response.SIVAResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/api/v2/test")
public class TestController extends NgxBaseController {

    @PostConstruct
    public void init() {
        this.setActiveSessionInfo(true);
        this.setActiveUserPrincipal(false);
    }


    @GetMapping(value = "/info")
    public @ResponseBody
    ResponseEntity<?> getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(SIVAResponseDTO.buildSIVAResponse(auth.getPrincipal()), HttpStatus.OK);
    }
}
