package com.phuocnguyen.app.ngxblobsuaa.config.oauthConfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngxsivaos.services.NgxSOrgHelperBaseService;
import com.ngxsivaos.services.impls.NgxSOrgHelperBaseServiceImpl;
import com.phuocnguyen.app.ngxblobssrv.service.*;
import com.phuocnguyen.app.ngxblobssrv.service.serviceImpl.*;
import com.sivaos.Configurer.SIVAJDBCConnectAutomation.SIVAJDBCConnectConfigurer;
import com.sivaos.Service.SIVAOSServiceImplement.BaseSIVAOSServiceImplement;
import com.sivaos.Service.SIVAOSServiceImplement.GlobalExceptionServiceImplement;
import com.sivaos.Service.SIVAOSServiceImplement.SIVAOSAuthenticationServiceImplement;
import com.sivaos.Service.SIVAOSServiceImplement.SIVAOSMailServiceImplement;
import com.sivaos.Utils.ConfigGlobalUtils;
import com.sivaos.config.propertiesConfig.AuthenticationProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
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
    @Resource
    public SIVAJDBCConnectConfigurer sivajdbcConnectConfigurer() {
        return new SIVAJDBCConnectConfigurer();
    }

    @Bean
    @Primary
    @Resource(name = "baseService")
    public BaseSIVAOSServiceImplement baseSIVAOSService() {
        return new BaseSIVAOSServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "sivaOsAuthenticationService")
    public SIVAOSAuthenticationServiceImplement sivaosAuthenticationServiceImplement() {
        return new SIVAOSAuthenticationServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "mailService")
    public SIVAOSMailServiceImplement sivaosMailServiceImplement() {
        return new SIVAOSMailServiceImplement();
    }


    @Bean
    @Primary
    @Resource(name = "globalExceptionService")
    public GlobalExceptionServiceImplement globalExceptionServiceImplement() {
        return new GlobalExceptionServiceImplement();
    }

    @Bean
    @Primary
    @Resource(name = "ngxLocationBaseService")
    public NgxLocationBaseService ngxLocationBaseService() {
        return new NgxLocationBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxProvincesBaseService")
    public NgxProvincesBaseService ngxProvincesBaseService() {
        return new NgxProvincesBaseServiceImpl(ngxLocationBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxDistrictsBaseService")
    public NgxDistrictsBaseService ngxDistrictsBaseService() {
        return new NgxDistrictsBaseServiceImpl(ngxProvincesBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxWardsBaseService")
    public NgxWardsBaseService ngxWardsBaseService() {
        return new NgxWardsBaseServiceImpl(ngxDistrictsBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxNeighborhoodsBaseService")
    public NgxNeighborhoodsBaseService ngxNeighborhoodsBaseService() {
        return new NgxNeighborhoodsBaseServiceImpl(ngxWardsBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxAddressesBaseService")
    public NgxAddressesBaseService ngxAddressesBaseService() {
        return new NgxAddressesBaseServiceImpl(
                ngxLocationBaseService(),
                ngxProvincesBaseService(),
                ngxDistrictsBaseService(),
                ngxWardsBaseService(),
                ngxNeighborhoodsBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxOrganizationBaseService")
    public NgxOrganizationBaseService ngxOrganizationBaseService() {
        return new NgxOrganizationBaseServiceImpl(ngxAddressesBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxCompaniesBaseService")
    public NgxCompaniesBaseService ngxCompaniesBaseService() {
        return new NgxCompaniesBaseServiceImpl(ngxOrganizationBaseService(),
                ngxAddressesBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxDepartmentsBaseService")
    public NgxDepartmentsBaseService ngxDepartmentsBaseService() {
        return new NgxDepartmentsBaseServiceImpl(ngxCompaniesBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxAddedByBaseService")
    public NgxAddedByBaseService ngxAddedByBaseService() {
        return new NgxAddedByBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxAllocateOodlesBaseService")
    public NgxAllocateOodlesBaseService ngxAllocateOodlesBaseService() {
        return new NgxAllocateOodlesBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxCategoriesAccountBaseService")
    public NgxCategoriesAccountBaseService ngxCategoriesAccountBaseService() {
        return new NgxCategoriesAccountBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxAppsBaseService")
    public NgxAppsBaseService ngxAppsBaseService() {
        return new NgxAppsBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxManagerialBusinessRolesBaseService")
    public NgxManagerialBusinessRolesBaseService ngxManagerialBusinessRolesBaseService() {
        return new NgxManagerialBusinessRolesBaseServiceImpl();
    }

    @Bean
    @Primary
    @Resource(name = "ngxTypeBusinessRolesBaseService")
    public NgxTypeBusinessRolesBaseService ngxTypeBusinessRolesBaseService() {
        return new NgxTypeBusinessRolesBaseServiceImpl(ngxAddedByBaseService());
    }

    @Bean
    @Primary
    @Resource(name = "ngxBusinessRolesBaseService")
    public NgxBusinessRolesBaseService ngxBusinessRolesBaseService() {
        return new NgxBusinessRolesBaseServiceImpl(ngxAddedByBaseService(), ngxTypeBusinessRolesBaseService());
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

    private static class OAuth2AccessTokenVariable {
        private static final String AUTHENTICATION = "Authorization";
    }
}
