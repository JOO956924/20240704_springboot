package com.example.ex5.service;

import com.example.ex5.dto.BoardDTO;
import com.example.ex5.dto.PageRequestDTO;
import com.example.ex5.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTests {

  @Autowired
  private BoardService boardService;

  @Test
  void register() {
    BoardDTO boardDTO = BoardDTO.builder()
        .title("Test")
        .content("Test Content")
        .writerEmail("user55@a.a")
        .build();
    Long bno = boardService.register(boardDTO);
  }

  @Test
  void getList() {
    PageRequestDTO pageRequestDTO = new PageRequestDTO();
    PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
    for (BoardDTO dto : result.getDtoList()) {
      System.out.println(dto);
    }
  }

  @Test
  void get() {
    Long bno = 100L;
    BoardDTO boardDTO = boardService.get(bno);
    System.out.println(boardDTO);
  }

  @Test
  void modify() {
  }

  @Test
  void removeWithReplies() {
    boardService.removeWithReplies(3L);
  }
}