package com.example.ex7.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {
      "/"
  };
  /* SecurityFilterChain Bean 열할 :
  세션 인증 기반 방식으로 대부분의 Spring Security에 대한 설정으로 다룰 수 있다. */
  @Bean
  protected SecurityFilterChain config(HttpSecurity httpSecurity)
      throws Exception{

    // authorizeHttpRequests :: 선별적으로 접속을 제한하는 메서드
    // 모든 페이지가 인증을 받도록 되어 있는 상태
    // httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
    httpSecurity.authorizeHttpRequests(
        auth -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated());
    httpSecurity.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
      @Override
      public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
        
      }
    });

    return httpSecurity.build();
  }
}
