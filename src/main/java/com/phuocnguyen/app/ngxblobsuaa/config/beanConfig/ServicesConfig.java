package com.phuocnguyen.app.ngxblobsuaa.config.beanConfig;

import com.ngxsivaos.services.NgxSOrgHelperBaseService;
import com.ngxsivaos.services.impls.NgxSOrgHelperBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersCallbackBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersDetailsBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.NgxUsersBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.NgxUsersCallbackBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.NgxUsersDetailsBaseServiceImpl;
import com.sivaos.config.propertiesConfig.AuthenticationProperties;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class ServicesConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Primary
    @Resource(name = "ngxUsersBaseService")
    public NgxUsersBaseService ngxUsersBaseService() {
        return new NgxUsersBaseServiceImpl();
    }

    @Bean
    @Primary
    public AuthenticationProperties authenticationProperties() {
        return new AuthenticationProperties();
    }

    @Bean
    @Primary
    @Resource(name = "ngxSOrgHelperBaseService")
    public NgxSOrgHelperBaseService ngxSOrgHelperBaseService() {
        return new NgxSOrgHelperBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxUsersDetailsBaseService")
    public NgxUsersDetailsBaseService ngxUsersDetailsBaseService() {
        return new NgxUsersDetailsBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxUsersCallbackBaseService")
    public NgxUsersCallbackBaseService ngxUsersCallbackBaseService() {
        return new NgxUsersCallbackBaseServiceImpl();
    }
}
