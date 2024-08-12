package com.example.exJSP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private static final String[] AUTH_WHITElIST = {
      "/"
  };

  @Bean
  protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(
        auth -> auth
            .requestMatchers(AUTH_WHITElIST).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/WEB-INF/views/**")).permitAll()
            .anyRequest().authenticated()
    );
    return httpSecurity.build();
  }
}