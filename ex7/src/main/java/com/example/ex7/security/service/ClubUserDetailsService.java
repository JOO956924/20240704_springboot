package com.example.ex7.security.service;

import com.example.ex7.entity.ClubMember;
import com.example.ex7.repository.ClubMemberRepository;
import com.example.ex7.security.dto.ClubMemberAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
  private final ClubMemberRepository clubMemberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("ClubMemberUser............" , username);
    Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);
    if (!result.isPresent()) throw new UsernameNotFoundException("Check Email or Social");
    ClubMember clubMember = result.get();

    ClubMemberAuthDTO clubMemberAuthDTO = new ClubMemberAuthDTO(
        clubMember.getEmail(), clubMember.getPassword(), clubMember.getCno(),
        clubMember.isFromSocial(),
        clubMember.getRoleSet().stream().map(
            clubMemberRole -> new SimpleGrantedAuthority(
                "ROLE_"+clubMemberRole.name())).collect(Collectors.toList())
    );

    return clubMemberAuthDTO;
  }
}
