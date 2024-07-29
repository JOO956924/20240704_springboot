package com.example.ex6.repository;

import com.example.ex6.entity.Movie;
import com.example.ex6.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @EntityGraph(attributePaths = {"member"},
      type = EntityGraph.EntityGraphType.FETCH)
  List<Review> findByMovie(Movie movie);

}
