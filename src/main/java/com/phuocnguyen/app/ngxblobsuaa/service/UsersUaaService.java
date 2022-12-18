package com.phuocnguyen.app.ngxblobsuaa.service;

import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.sivaos.Model.UserDTO;

public interface UsersUaaService {

    UserDTO findUserBy(UsersFilter filter);
}
