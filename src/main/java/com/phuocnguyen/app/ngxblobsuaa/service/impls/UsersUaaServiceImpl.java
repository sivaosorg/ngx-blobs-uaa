package com.phuocnguyen.app.ngxblobsuaa.service.impls;

import com.ngxsivaos.model.enums.GeolocationType;
import com.ngxsivaos.model.enums.RedisPropsType;
import com.ngxsivaos.model.enums.RedisStylesType;
import com.ngxsivaos.model.request.RedisStylesRequest;
import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.phuocnguyen.app.ngxblobssrv.service.NgxRedisStylesBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersDetailsBaseService;
import com.phuocnguyen.app.ngxblobsuaa.service.UsersUaaService;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utility.CollectionsUtility;
import com.sivaos.Utility.StringUtility;
import com.sivaos.Utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"FieldCanBeLocal"})
@Service(value = "usersUaaService")
@Transactional
public class UsersUaaServiceImpl implements UsersUaaService {

    private static final Logger logger = LoggerFactory.getLogger(UsersUaaServiceImpl.class);

    private static final Integer KEY_USER_LIVE_TIMEOUT = 3; // 3 minutes
    private final NgxUsersDetailsBaseService ngxUsersDetailsBaseService;
    private final NgxRedisStylesBaseService ngxRedisStylesBaseService;

    @Value("${sivaos.cache.callbacks.user-details.enabled:false}")
    private boolean enableCallbacksUserDetails;

    @Value("${sivaos.cache.key-master:uaa}")
    private String keyMasterRedis;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UsersUaaServiceImpl(
            NgxUsersDetailsBaseService ngxUsersDetailsBaseService,
            NgxRedisStylesBaseService ngxRedisStylesBaseService
    ) {
        this.ngxUsersDetailsBaseService = ngxUsersDetailsBaseService;
        this.ngxRedisStylesBaseService = ngxRedisStylesBaseService;
    }

    @PostConstruct
    private void initialize() {
        keyMasterRedis = StringUtility.isNotEmpty(keyMasterRedis) ? keyMasterRedis : "uaa";
        enableCallbacksUserDetails = ObjectUtils.allNotNull(enableCallbacksUserDetails) && enableCallbacksUserDetails;
    }

    @Override
    public UserDTO findUserBy(UsersFilter filter) {
        RedisStylesRequest redisStylesRequest = new RedisStylesRequest();
        UserDTO user;
        
        filter.setSizeOfSessions(2);
        filter.setViewParentsSessions(true);
        filter.setViewParentsSysModulePerms(true);
        filter.setViewParentsGroupUsers(true);
        filter.setViewParentsUsersProperties(true);
        filter.setSizeOfRecordsUserProperties(2);


        redisStylesRequest.setMasterKey(keyMasterRedis);
        redisStylesRequest.setRedisKey(filter.getUsername());
        redisStylesRequest.setRedisStylesType(RedisStylesType.LOGIN_KEY);
        redisStylesRequest.setGeolocationType(GeolocationType.VIETNAM_GEOLOCATION);
        redisStylesRequest.setRedisPropsType(RedisPropsType.ObjectType);

        if (enableCallbacksUserDetails) {
            if (ngxRedisStylesBaseService.isAvailable()) {
                user = ngxRedisStylesBaseService.getCacheObject(redisStylesRequest);
                if (ObjectUtils.allNotNull(user)) {
                    return user;
                }
            }
        }

        List<UserDTO> users = ngxUsersDetailsBaseService.findUsersBy(filter, entityManager);
        user = CollectionsUtility.isNotEmpty(users) ? users.get(0) : null;

        if (enableCallbacksUserDetails) {
            if (ngxRedisStylesBaseService.isAvailable()) {
                if (ObjectUtils.allNotNull(user)) {
                    ngxRedisStylesBaseService.setCacheObject(redisStylesRequest, user, KEY_USER_LIVE_TIMEOUT, TimeUnit.MINUTES);
                }
            }
        }

        return user;
    }
}
