package com.example.ex7.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class ClubMemberAuthDTO extends User {
  private Long cno;
  private String email;
  private boolean fromSocial;

  public ClubMemberAuthDTO(String username, String password,
                           Long cno, boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.cno = cno;
    this.email = username; // 별 UserDetails 에서 username은 이메일을 기준으로 하기 때문.
    this.fromSocial = fromSocial;
  }
}
