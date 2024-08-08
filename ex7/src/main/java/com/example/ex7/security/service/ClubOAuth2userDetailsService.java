package com.example.ex7.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ClubOAuth2userDetailsService extends DefaultOAuth2UserService {

  private static final String NAVER = "naver";
  private static final String KAKAO = "kakao";

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("================ userRequest: " + userRequest);
    String clientName = userRequest.getClientRegistration().getClientName();
    log.info("clientName>>", clientName);
    log.info("info_from_google>>", userRequest.getAdditionalParameters());
    OAuth2User oAuth2User = super.loadUser(userRequest);
    oAuth2User.getAttributes().forEach((k,v) -> log.info(k+":"+v));
    return super.loadUser(userRequest);
  }
}
