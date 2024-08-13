package com.example.ex8.config;

import com.example.ex8.security.filter.ApiCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ApiCheckFilter apiCheckFilter() {
    return new ApiCheckFilter(new String[]{"/notes/**"});
  }

  @Bean
  protected SecurityFilterChain config(HttpSecurity httpSecurity)
      throws Exception {
    // API 서버에서는 csrf 사용안함
    httpSecurity.csrf(httpSecurityCsrfConfigurer -> {
      httpSecurityCsrfConfigurer.disable();
    });

    httpSecurity.authorizeHttpRequests(
        auth -> auth
            .requestMatchers(new AntPathRequestMatcher("/notes/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/error/**")).permitAll()
            .anyRequest().denyAll());

    // addFilterBefore는 일반적인 필터링 순서보다 앞쪽에서 필터링하도록 순서 조정
    httpSecurity.addFilterBefore(
        apiCheckFilter(),UsernamePasswordAuthenticationFilter.class);
    // BasicAuthenticationFilter.class 도 사용가능
    return httpSecurity.build();
  }

}