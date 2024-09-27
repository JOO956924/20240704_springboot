package com.example.InstaPrj.service;

import com.example.InstaPrj.dto.ReviewDTO;
import com.example.InstaPrj.entity.Feed;
import com.example.InstaPrj.entity.Review;
import com.example.InstaPrj.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;

  @Override
  public List<ReviewDTO> getListOfFeed(Long fno) {
    List<Review> result = reviewRepository.findByFeed(
        Feed.builder().fno(fno).build());
    return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
  }

  @Override
  public Long register(ReviewDTO reviewDTO) {
    log.info("reviewDTO >> ",reviewDTO);
    Review review = dtoToEntity(reviewDTO);
    reviewRepository.save(review);
    return review.getReviewnum();
  }

  @Override
  public void modify(ReviewDTO reviewDTO) {
    Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());
    if (result.isPresent()) {
      Review review = result.get();
      review.changeGrade(reviewDTO.getGrade());
      review.changeText(reviewDTO.getText());
      reviewRepository.save(review);
    }
  }

  @Override
  public void remove(Long reviewnum) {
    reviewRepository.deleteById(reviewnum);
  }
}
