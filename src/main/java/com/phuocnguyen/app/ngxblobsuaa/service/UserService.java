package com.phuocnguyen.app.ngxblobsuaa.service;

import com.sivaos.Model.Request.Original.SignInRequest;
import com.sivaos.Model.Response.SIVAResponseDTO;

public interface UserService {

    SIVAResponseDTO<?> login(SignInRequest signInRequest);
}
