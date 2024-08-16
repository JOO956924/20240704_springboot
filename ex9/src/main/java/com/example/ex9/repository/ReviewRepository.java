package com.example.ex9.repository;

import com.example.ex9.entity.Feed;
import com.example.ex9.entity.Member;
import com.example.ex9.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @EntityGraph(attributePaths = {"member"},
      type = EntityGraph.EntityGraphType.FETCH)
  List<Review> findByFeed(Feed feed);

  @Modifying
  @Query("delete from Review r where r.member = :member")
  void deleteByMember(Member member);

  @Modifying //update, deltet할 때 항상 표기
  @Query("delete from Review r where r.feed.fno=:fno")
  void deleteByFno(@Param("fno") Long fno);
}
