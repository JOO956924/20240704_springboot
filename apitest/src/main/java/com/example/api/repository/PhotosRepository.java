package com.example.api.repository;

import com.example.api.entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotosRepository extends JpaRepository<Photos, Long> {
  @Modifying
  @Query("delete from Photos p where p.boards.bno=:bno")
  void deleteByBno(@Param("bno") long bno);

  @Modifying
  @Query("delete from Photos p where p.uuid=:uuid")
  void deleteByUuid(@Param("uuid")String uuid);

  @Query("select p from Photos p where p.boards.bno=:bno")
  List<Photos> findByMid(@Param("bno") Long bno);
}
