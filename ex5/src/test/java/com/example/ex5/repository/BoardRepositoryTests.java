package com.example.ex5.repository;

import com.example.ex5.entity.Board;
import com.example.ex5.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {
  @Autowired
  BoardRepository boardRepository;
  @Test
  public void insertBoards() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Member member = Member.builder().email("user" + i + "@a.a").build();
      Board board = Board.builder()
          .title("Title..." + i)
          .content("Content..." + i)
          .writer(member)
          .build();
      boardRepository.save(board);
    });
  }

  @Transactional
  @Test
  public void testRead1() {
    Optional<Board> result = boardRepository.findById(100L);
    if (result.isPresent()) {
      Board board = result.get();
      System.out.println(">>"+board);
      System.out.println(">>"+board.getWriter());
    }
  }
}