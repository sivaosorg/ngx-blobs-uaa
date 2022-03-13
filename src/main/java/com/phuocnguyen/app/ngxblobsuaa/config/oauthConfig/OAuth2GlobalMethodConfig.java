package com.phuocnguyen.app.ngxblobsuaa.config.oauthConfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngxsivaos.services.NgxSOrgHelperBaseService;
import com.ngxsivaos.services.impls.NgxSOrgHelperBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersCallbackBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersDetailsBaseService;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.NgxUsersBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.NgxUsersCallbackBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.NgxUsersDetailsBaseServiceImpl;
import com.sivaos.Utils.ConfigGlobalUtils;
import com.sivaos.config.propertiesConfig.AuthenticationProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@Profile(value = {"dev", "local", "prod"})
public class OAuth2GlobalMethodConfig extends GlobalMethodSecurityConfiguration {

    @Value("${sivaos.geo.time-zone}")
    private String timezone;

    @Bean
    public ObjectMapper objectMapper() {
        return ConfigGlobalUtils.configureDateTimeGlobal(timezone);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList(OAuth2AccessTokenVariable.AUTHENTICATION));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

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

    private static class OAuth2AccessTokenVariable {
        private static final String AUTHENTICATION = "Authorization";
    }
}
