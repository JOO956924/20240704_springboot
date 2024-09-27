package com.example.InstaPrj.repository;

import com.example.InstaPrj.entity.Feed;
import com.example.InstaPrj.entity.Photos;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedRepositoryTests {
  @Autowired
  FeedRepository feedRepository;

  @Autowired
  PhotosRepository photosRepository;

  @Transactional
  @Commit
  @Test
  public void insertFeeds() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Feed feed = Feed.builder().title("Feed..." + i).build();
      feedRepository.save(feed);
      System.out.println("----------------------------");
      int cnt = (int) (Math.random() * 5) + 1;
      for (int j = 0; j < cnt; j++) {
        Photos photos = Photos.builder()
            .uuid(UUID.randomUUID().toString())
            .feed(feed)
            .imgName("test" + j + ".jpg")
            .build();
        photosRepository.save(photos);
      }
    });
  }

}