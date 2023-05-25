package com.bwgjoseph.springsecuritystackoverflowerror;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@EnableWebSecurity(debug = true)
@Configuration
public class WebSecurityConfig {
    /**
     * This allows us to get an instance of the {@code AuthenticationManager} so to inject into {@code RequestHeaderAuthenticationFilter}
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter(AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("X-User");
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(true);
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return requestHeaderAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
            .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
            .addFilter(requestHeaderAuthenticationFilter(authenticationManager))
            .build();
    }

}
