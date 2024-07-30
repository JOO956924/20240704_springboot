package com.example.ex6.repository;

import com.example.ex6.entity.Member;
import com.example.ex6.entity.Movie;
import com.example.ex6.entity.Review;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTests {
  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  MemberRepository memberRepository;

  @Test
  public void insertReviews() {
    IntStream.rangeClosed(1, 200).forEach(i -> {
      Long mno = (long) (Math.random() * 100) + 1;
      Long mid = (long) (Math.random() * 100) + 1;
      Review review = Review.builder()
          .member(Member.builder().mid(mid).build())
          .movie(Movie.builder().mno(mno).build())
          .grade((int) (Math.random() * 5) + 1)
          .text("이 영화는.....")
          .build();
      reviewRepository.save(review);
    });
  }

  @Test
  public void testFindByMovie() {
    List<Review> result = reviewRepository.findByMovie(
        Movie.builder().mno(100L).build()
    );
    result.forEach(review -> {
      System.out.println(review.getReviewnum());
      System.out.println(review.getGrade());
      System.out.println(review.getText());
      System.out.println(review.getMember().getEmail());
      System.out.println();
    });
  }

  @Test
  @Transactional
  @Commit
  public void testDeleteByMember() {
    Long mid = 1L;
    Member member = Member.builder().mid(mid).build();
    reviewRepository.deleteByMember(member);
    memberRepository.deleteById(mid);
  }
}