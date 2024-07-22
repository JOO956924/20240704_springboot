package com.example.ex5.repository;

import com.example.ex5.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTests {

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void insertMembers() {
    IntStream.rangeClosed(1,100).forEach(new IntConsumer() {
      @Override
      public void accept(int value) {
        Member member = Member.builder()
            .email()
            .build();
      }
    });
    IntStream.rangeClosed(1,100).map(new IntUnaryOperator() {
      @Override
      public int applyAsInt(int operand) {
        return 0;
      }
    });
  }

}