package com.phuocnguyen.app.ngxblobsuaa.service.impls;

import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersDetailsBaseService;
import com.phuocnguyen.app.ngxblobsuaa.service.UsersUaaService;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utility.CollectionsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service(value = "usersUaaService")
@Transactional
public class UsersUaaServiceImpl implements UsersUaaService {

    private static final Logger logger = LoggerFactory.getLogger(UsersUaaServiceImpl.class);

    private final NgxUsersDetailsBaseService ngxUsersDetailsBaseService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UsersUaaServiceImpl(
            NgxUsersDetailsBaseService ngxUsersDetailsBaseService) {
        this.ngxUsersDetailsBaseService = ngxUsersDetailsBaseService;
    }

    @Override
    public UserDTO findUserBy(UsersFilter usersFilter) {
        usersFilter.setViewParentsSessions(true);
        usersFilter.setSizeOfSessions(1);
        List<UserDTO> users = ngxUsersDetailsBaseService.findUsersBy(usersFilter, entityManager);
        return CollectionsUtility.isNotEmpty(users) ? users.get(0) : null;
    }
}
