package com.example.warehouses.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()

                .requestMatchers("/auth/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/somepath/**").permitAll()

                .requestMatchers("/api/delete/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()

//Конфигурира вписването в приложението
                .and().formLogin()
                .and().httpBasic();

//Конфигурира изхода от приложението
        http.logout(logout -> logout.logoutUrl("api/auth/logout")
                .invalidateHttpSession(true));

//Задава политика за управление на сесията
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

//и т.н.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();//new BCryptPasswordEncoder(); //
    }






}
