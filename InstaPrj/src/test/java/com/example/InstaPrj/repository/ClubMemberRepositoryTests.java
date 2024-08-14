package com.example.InstaPrj.repository;

import com.example.InstaPrj.entity.ClubMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubMemberRepositoryTests {
  @Autowired
  private ClubMemberRepository clubMemberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void testRead() {
    Optional<ClubMember> result = clubMemberRepository.findByEmail("user100@a.a");
    if (result.isPresent()) System.out.println(result.get());
  }
}