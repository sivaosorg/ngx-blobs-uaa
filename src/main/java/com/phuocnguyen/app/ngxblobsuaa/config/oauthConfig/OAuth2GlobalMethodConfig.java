package com.phuocnguyen.app.ngxblobsuaa.config.oauthConfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivaos.Utils.ConfigGlobalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@SuppressWarnings({
        "SpellCheckingInspection"
})
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@Profile(value = {"dev", "local", "prod"})
public class OAuth2GlobalMethodConfig extends GlobalMethodSecurityConfiguration {

    @Value("${sivaos.geo.time-zone:Asia/Ho_Chi_Minh}")
    private String timezone;

    @Bean
    public ObjectMapper createInstance() {
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

    private static class OAuth2AccessTokenVariable {
        private static final String AUTHENTICATION = "Authorization";
    }
}
