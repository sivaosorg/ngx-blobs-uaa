package com.phuocnguyen.app.ngxblobsuaa.service.impls;

import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.phuocnguyen.app.ngxblobssrv.service.NgxRedisBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersDetailsBaseService;
import com.phuocnguyen.app.ngxblobsuaa.service.UsersUaaService;
import com.sivaos.Configurer.RedisConfigurer.Utils.RedisKeys;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utility.CollectionsUtility;
import com.sivaos.Utility.StringUtility;
import com.sivaos.Utils.LoggerUtils;
import com.sivaos.Utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final NgxUsersDetailsBaseService ngxUsersDetailsBaseService;
    private final NgxRedisBaseService ngxRedisBaseService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${sivaos.cache.callbacks.user-details.enabled}")
    private boolean enableCallbacksUserDetails;

    @Value("${sivaos.cache.key-master}")
    private String keyMasterRedis;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UsersUaaServiceImpl(
            NgxUsersDetailsBaseService ngxUsersDetailsBaseService,
            NgxRedisBaseService ngxRedisBaseService,
            RedisTemplate<String, Object> redisTemplate) {
        this.ngxUsersDetailsBaseService = ngxUsersDetailsBaseService;
        this.ngxRedisBaseService = ngxRedisBaseService;
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void initialize() {
        keyMasterRedis = StringUtility.isNotEmpty(keyMasterRedis) ? keyMasterRedis : "uaa";
        enableCallbacksUserDetails = ObjectUtils.allNotNull(enableCallbacksUserDetails) && enableCallbacksUserDetails;
    }

    @Override
    public UserDTO findUserBy(UsersFilter usersFilter) {
        usersFilter.setViewParentsSessions(true);
        usersFilter.setSizeOfSessions(1);
        String key = String.format("%s:%s", keyMasterRedis, RedisKeys.getLoginKey(usersFilter.getUsername()));
        UserDTO user;

        if (enableCallbacksUserDetails) {
            String userDetailsCached = ngxRedisBaseService.getCacheObject(redisTemplate, key);
            user = LoggerUtils.parseStrToObs(userDetailsCached, UserDTO.class);
            if (ObjectUtils.allNotNull(user)) {
                return user;
            }
        }

        List<UserDTO> users = ngxUsersDetailsBaseService.findUsersBy(usersFilter, entityManager);
        user = CollectionsUtility.isNotEmpty(users) ? users.get(0) : null;

        if (enableCallbacksUserDetails) {
            if (ObjectUtils.allNotNull(user)) {
                ngxRedisBaseService.setCacheObject(redisTemplate, key, LoggerUtils.parseObsToStrUsingGson(user), 5, TimeUnit.MINUTES);
            }
        }

        return user;
    }
}
