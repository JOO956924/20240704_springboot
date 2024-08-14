package com.example.InstaPrj.repository;

import com.example.InstaPrj.entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotosRepository extends JpaRepository<Photos, Long> {
  @Modifying
  @Query("delete from Photos p where p.feed.fno=:fno")
  void deleteByFno(@Param("fno") long fno);

  @Modifying
  @Query("delete from Photos p where p.uuid=:uuid")
  void deleteByUuid(@Param("uuid")String uuid);

  @Query("select p from Photos p where p.feed.fno=:fno")
  List<Photos> findByFno(@Param("fno") Long fno);
}
