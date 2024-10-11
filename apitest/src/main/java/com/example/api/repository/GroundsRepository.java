package com.example.api.repository;

import com.example.api.entity.Boards;
import com.example.api.entity.Grounds;
import com.example.api.repository.search.SearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GroundsRepository extends JpaRepository<Grounds, Long>, SearchRepository {

  @Query("SELECT g.sports, g.matchStartDate, g.location FROM Grounds g " +
      "WHERE g.gno = :gno ")
  List<Object[]> findByGnoAndNotFinished(Long gno);

}