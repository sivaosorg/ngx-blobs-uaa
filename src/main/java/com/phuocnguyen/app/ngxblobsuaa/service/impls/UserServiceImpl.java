package com.phuocnguyen.app.ngxblobsuaa.service.impls;

import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersBaseService;
import com.phuocnguyen.app.ngxblobsuaa.service.UserService;
import com.sivaos.Model.Request.Original.SignInRequest;
import com.sivaos.Model.Response.SIVAResponseDTO;
import com.sivaos.config.propertiesConfig.AuthenticationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "usersService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final NgxUsersBaseService ngxUsersBaseService;
    private final AuthenticationProperties authenticationProperties;

    @Autowired
    public UserServiceImpl(
            NgxUsersBaseService ngxUsersBaseService,
            AuthenticationProperties authenticationProperties) {
        this.ngxUsersBaseService = ngxUsersBaseService;
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public SIVAResponseDTO<?> login(SignInRequest signInRequest) {
        return ngxUsersBaseService.loginDetails(signInRequest, authenticationProperties);
    }
}
