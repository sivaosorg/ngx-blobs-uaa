package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobssrv.controllers.NgxBaseController;
import com.phuocnguyen.app.ngxblobsuaa.NgxBlobsUaaApplication;
import com.sivaos.Model.Response.SIVAResponseDTO;
import com.sivaos.Utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import static com.ngxsivaos.variable.RoutesApiVariable.ROUTES_API_USER_SELF;

@SuppressWarnings({"FieldCanBeLocal"})
@RestController
@RequestMapping(value = ROUTES_API_USER_SELF)
public class UsersSelfController extends NgxBaseController {

    private ApplicationContext applicationContext;

    @Value("${spring.profiles.active}")
    private String profileActive;

    @PostConstruct
    public void init() {
        this.setActiveSessionInfo(true);
        this.setActiveUserPrincipal(false);
        this.setProfile(profileActive);
    }

    @SuppressWarnings("SameParameterValue")
    private void initiateAppShutdown(int returnCode) {
        SpringApplication.exit(applicationContext, () -> returnCode);
    }

    @GetMapping("/info")
    public @ResponseBody
    ResponseEntity<?> findSelfInfo() {
        logger.info("Users request: {}, has permission: {}",
                LoggerUtils.toJson(userRequest),
                LoggerUtils.toJson(this.userRequest.getPrivileges()));
        return new ResponseEntity<>(SIVAResponseDTO.buildSIVAResponse(userRequest), HttpStatus.OK);
    }

    @GetMapping("/restart")
    void restart2() {
        Thread restartThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                NgxBlobsUaaApplication.restart();
            } catch (InterruptedException ignored) {
            }
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }

    @RequestMapping("/shutdown")
    public void shutdown() {
        initiateAppShutdown(0);
    }
}
