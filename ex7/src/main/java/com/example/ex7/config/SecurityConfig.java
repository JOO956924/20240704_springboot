package com.example.ex7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] AUTH_WHITElIST = {
      "/", "/sample/all"
  };

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /* SecurityFilterChain Bean 역할 :
  세션 인증 기반 방식으로 대부분의 Spring Security에 대한 설정으로 다룰 수 있다. */
  @Bean
  protected SecurityFilterChain config(HttpSecurity httpSecurity)
      throws Exception {

    // authorizeHttpRequests :: 선별적으로 접속을 제한하는 메서드
    // 모든 페이지가 인증을 받도록 되어 있는 상태
    // httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
    httpSecurity.authorizeHttpRequests(
        auth -> auth.requestMatchers(AUTH_WHITElIST).permitAll()
            .requestMatchers("/sample/admin/**").hasRole("ADMIN")
            .requestMatchers("/sample/member/**").access(
                new WebExpressionAuthorizationManager(
                    "hasRole('ADMIN') or hasRole('MEMBER')")
            )
            .anyRequest().authenticated());

    // formLogin()를 정의만 해도 자동생성된 로그인페이지로 이동가능.
    httpSecurity.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
      @Override
      public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {

      }
    });


    return httpSecurity.build();
  }

  // InMemory 방식으로 계정 관리
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user1 = User.builder()
        .username("user1")
        .password("$2a$10$9G92Uj6fazBFRrpYxY0.fO6d0U7H/wxqxAvh2w2Wu.Bk5pvgpT33q")
        .roles("USER")
        .build();
    UserDetails member = User.builder()
        .username("member")
        .password("$2a$10$ge.irY0lMJXguMEss3bFfetwh/YLdjSHpZMWW1qnbemeUvsnaZHPW")
        .roles("MEMBER")
        .build();
    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("1"))
        .roles("ADMIN","MEMBER")
        .build();
    List<UserDetails> list = new ArrayList<>();
    list.add(user1);list.add(member);list.add(admin);
    return new InMemoryUserDetailsManager(list);
  }

}
