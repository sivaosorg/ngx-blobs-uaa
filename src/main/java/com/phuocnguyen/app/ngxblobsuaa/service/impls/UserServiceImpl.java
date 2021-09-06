package com.phuocnguyen.app.ngxblobsuaa.service.impls;

import com.phuocnguyen.app.ngxblobssrv.model.request.GrantAuthorizationRequest;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersBaseService;
import com.phuocnguyen.app.ngxblobsuaa.service.UserService;
import com.sivaos.Model.Request.Original.SignInRequest;
import com.sivaos.Model.Response.SIVAResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service(value = "usersService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static GrantAuthorizationRequest grantAuthorizationRequest;
    private final NgxUsersBaseService ngxUsersBaseService;

    @Value("${sivaos.uaa.client-host}")
    private String hostOauth;

    @Value("${sivaos.uaa.client-id}")
    private String clientId;

    @Value("${sivaos.uaa.client-secret}")
    private String clientSecret;

    @Autowired
    public UserServiceImpl(
            NgxUsersBaseService ngxUsersBaseService) {
        this.ngxUsersBaseService = ngxUsersBaseService;
    }

    @PostConstruct
    public void init() {
        grantAuthorizationRequest = new GrantAuthorizationRequest();
        grantAuthorizationRequest.setHost(hostOauth);
        grantAuthorizationRequest.setClientId(clientId);
        grantAuthorizationRequest.setClientSecret(clientSecret);
    }

    @Override
    public SIVAResponseDTO<?> login(SignInRequest signInRequest) {
        return ngxUsersBaseService.loginDetails(signInRequest, grantAuthorizationRequest);
    }
}
