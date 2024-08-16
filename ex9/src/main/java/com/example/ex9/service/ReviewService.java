package com.example.ex9.service;



import com.example.ex9.dto.ReviewDTO;
import com.example.ex9.entity.Feed;
import com.example.ex9.entity.Member;
import com.example.ex9.entity.Review;

import java.util.List;

public interface ReviewService {
  List<ReviewDTO> getListOfFeed(Long mno);
  
  Long register(ReviewDTO reviewDTO);

  void modify(ReviewDTO reviewDTO);

  void remove(Long reviewnum);

  public default Review dtoToEntity(ReviewDTO reviewDTO) {
    Review review = Review.builder()
        .reviewnum(reviewDTO.getReviewnum())
        .feed(Feed.builder().fno(reviewDTO.getFno()).build())
        .member(Member.builder().mid(reviewDTO.getMid()).build())
        .grade(reviewDTO.getGrade())
        .text(reviewDTO.getText())
        .build();
    return review;
  }

  default ReviewDTO entityToDto(Review review) {
    ReviewDTO reviewDTO = ReviewDTO.builder()
        .reviewnum(review.getReviewnum())
        .fno(review.getFeed().getFno())
        .mid(review.getMember().getMid())
        .name(review.getMember().getNickname())
        .email(review.getMember().getEmail())
        .grade(review.getGrade())
        .text(review.getText())
        .regDate(review.getRegDate())
        .modDate(review.getModDate())
        .build();
    return reviewDTO;
  }
}
