package com.example.ex8.security;

import com.example.ex8.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTUtilTests {
  private JWTUtil jwtUtil;

  @BeforeEach
  public void testBefore() {
    System.out.println("test before......");
    jwtUtil = new JWTUtil();
  }
  @Test
  public void testEncode() throws Exception {
    String email = "user95@a.a";
    String token = jwtUtil.generateToken(email);
    System.out.println(token);
  }
}