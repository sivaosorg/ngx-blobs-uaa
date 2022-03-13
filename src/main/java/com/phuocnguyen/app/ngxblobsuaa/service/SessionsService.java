package com.phuocnguyen.app.ngxblobsuaa.service;

import com.phuocnguyen.app.ngxblobssrv.model.filter.SessionsFilter;
import com.sivaos.Model.Response.SessionsResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Service.IERSIVAOSGenericService;

import java.util.List;

public interface SessionsService extends IERSIVAOSGenericService<SessionsResponseDTO, Long, Long, SessionsResponseDTO> {

    List<SessionsResponseDTO> findSessionsBy(SessionsFilter sessionsFilter);

    void logoutSessions(UserDTO userDTO);
}
