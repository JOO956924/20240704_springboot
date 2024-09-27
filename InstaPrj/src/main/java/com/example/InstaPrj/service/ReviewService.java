package com.example.InstaPrj.service;



import com.example.InstaPrj.dto.ReviewDTO;
import com.example.InstaPrj.entity.ClubMember;
import com.example.InstaPrj.entity.Feed;
import com.example.InstaPrj.entity.Review;

import java.util.List;

public interface ReviewService {
  List<ReviewDTO> getListOfFeed(Long fno);
  
  Long register(ReviewDTO reviewDTO);

  void modify(ReviewDTO reviewDTO);

  void remove(Long reviewnum);

  public default Review dtoToEntity(ReviewDTO reviewDTO) {
    Review review = Review.builder()
        .reviewnum(reviewDTO.getReviewnum())
        .feed(Feed.builder().fno(reviewDTO.getFno()).build())
        .clubMember(ClubMember.builder().cno(reviewDTO.getCno()).build())
        .grade(reviewDTO.getGrade())
        .text(reviewDTO.getText())
        .build();
    return review;
  }

  default ReviewDTO entityToDto(Review review) {
    ReviewDTO reviewDTO = ReviewDTO.builder()
        .reviewnum(review.getReviewnum())
        .fno(review.getFeed().getFno())
        .cno(review.getClubMember().getCno())
        .name(review.getClubMember().getName())
        .email(review.getClubMember().getEmail())
        .grade(review.getGrade())
        .text(review.getText())
        .regDate(review.getRegDate())
        .modDate(review.getModDate())
        .build();
    return reviewDTO;
  }
}
