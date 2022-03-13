package com.phuocnguyen.app.ngxblobsuaa.controller;

import com.phuocnguyen.app.ngxblobssrv.controllers.NgxBaseController;
import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.phuocnguyen.app.ngxblobsuaa.service.SessionsService;
import com.phuocnguyen.app.ngxblobsuaa.service.UsersUaaService;
import com.sivaos.Model.Response.SIVAResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static com.ngxsivaos.variable.RoutesApiVariable.ROUTES_API_USER_SELF;

@SuppressWarnings({"FieldCanBeLocal"})
@RestController
@RequestMapping(value = ROUTES_API_USER_SELF)
public class UsersSelfController extends NgxBaseController {

    private final UsersUaaService usersUaaService;
    private final SessionsService sessionsService;

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Autowired
    public UsersSelfController(
            UsersUaaService usersUaaService,
            SessionsService sessionsService) {
        this.usersUaaService = usersUaaService;
        this.sessionsService = sessionsService;
    }

    @PostConstruct
    public void init() {
        this.setActiveSessionInfo(false);
        this.setActiveUserPrincipal(false);
        this.setActiveUserDetailsRequest(false);
        this.setProfile(profileActive);
    }

    @GetMapping("/info")
    public @ResponseBody
    ResponseEntity<?> findSelfInfo() {
        UsersFilter usersFilter = new UsersFilter();
        usersFilter.setPageIndex(1);
        usersFilter.setPageSize(1);
        usersFilter.setUsername(this.usernameAccess);
        usersFilter.setViewParentsSysModulePerms(true);
        return new ResponseEntity<>(SIVAResponseDTO.buildSIVAResponse(usersUaaService.findUserBy(usersFilter)), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public @ResponseBody
    ResponseEntity<?> logout(HttpServletRequest request) {
        sessionsService.logoutSessions(this.userRequest);
        return new ResponseEntity<>(SIVAResponseDTO.buildSivaResponseMessage("You have successfully logged out"), HttpStatus.OK);
    }

}
