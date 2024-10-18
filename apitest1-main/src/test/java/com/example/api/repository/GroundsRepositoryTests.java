package com.example.api.repository;

import com.example.api.entity.Gphotos;
import com.example.api.entity.Grounds;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroundsRepositoryTests {
  @Autowired
  GroundsRepository groundsRepository;

  @Autowired
  GphotosRepository gphotosRepository;

  @Transactional
  @Commit
  @Test
  public void insertGrounds() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Grounds grounds = Grounds.builder().gtitle("Grounds..." + i)
          .groundsTime("오후 "+i+"시")
          .price(10000)
          .location("부산")
          .build();
      groundsRepository.save(grounds);
      System.out.println("----------------------------");
      int cnt = (int) (Math.random() * 5) + 1;
      for (int j = 0; j < cnt; j++) {
        Gphotos gphotos = Gphotos.builder()
            .uuid(UUID.randomUUID().toString())
            .grounds(grounds)
            .gphotosName("test" + j + ".jpg")
            .build();
        gphotosRepository.save(gphotos);
      }
    });
  }

}