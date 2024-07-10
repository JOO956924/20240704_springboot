package com.example.ex4.repository;

import com.example.ex4.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookRepositoryTests {

  @Autowired
  private GuestbookRepository guestbookRepository;

  @Test
  public void insertDummies() {
    IntStream.rangeClosed(1, 300).forEach(new IntConsumer() {
      @Override
      public void accept(int i) {
        Guestbook guestbook = Guestbook.builder()
            .title("Title..." + i)
            .content("Content..." + i)
            .writer("user"+(i%10))
            .build();
        System.out.println(guestbookRepository.save(guestbook));
      }
    });
  }
}