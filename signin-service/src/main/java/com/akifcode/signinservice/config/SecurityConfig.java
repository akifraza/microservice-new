package com.akifcode.signinservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.akifcode.signinservice.filters.JwtRequestFilter;
import com.akifcode.signinservice.services.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
// @EnableWebFluxSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private CustomUserDetailService customUserDetailService;
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
         * serverHttpSecurity.csrf()
         * .disable()
         * .authorizeExchange(exchange -> exchange.pathMatchers("/signin"))
         * .and()
         * .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**"))
         * .permitAll()
         * .anyExchange()
         * .authenticated()
         * .and()
         * .sessionManagement()
         * .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         * 
         * 
         * serverHttpSecurity
         * .addFilterBefore(jwtRequestFilter,
         * UsernamePasswordAuthenticationFilter.class);
         * //.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
         * 
         * return serverHttpSecurity.build();
         */

        http
                .csrf()
                .disable()
                .authorizeRequests().antMatchers("/api/authenticate").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
        return defaultSecurityFilterChain;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
