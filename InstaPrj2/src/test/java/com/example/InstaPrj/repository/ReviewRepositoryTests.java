package com.example.InstaPrj.repository;

import com.example.InstaPrj.entity.ClubMember;
import com.example.InstaPrj.entity.Feed;
import com.example.InstaPrj.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTests {
  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  ClubMemberRepository clubMemberRepository;

  @Test
  public void insertReviews() {
    IntStream.rangeClosed(1, 200).forEach(i -> {
      Long fno = (long) (Math.random() * 100) + 1;
      Long cno = (long) (Math.random() * 100) + 1;
      Review review = Review.builder()
          .clubMember(ClubMember.builder().cno(cno).build())
          .feed(Feed.builder().fno(fno).build())
          .grade((int) (Math.random() * 5) + 1)
          .text("이 게시물은.....")
          .build();
      reviewRepository.save(review);
    });
  }

}