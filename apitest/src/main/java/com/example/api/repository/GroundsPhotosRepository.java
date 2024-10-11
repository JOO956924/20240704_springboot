package com.example.api.repository;

import com.example.api.entity.GroundsPhotos;
import com.example.api.entity.GroundsPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroundsPhotosRepository extends JpaRepository<GroundsPhotos, Long> {
  @Modifying
  @Query("delete from GroundsPhotos p where p.grounds.gno=:gno")
  void deleteByGno(@Param("gno") long gno);

  @Modifying
  @Query("delete from GroundsPhotos p where p.uuid=:uuid")
  void deleteByUuid(@Param("uuid")String uuid);

  @Query("select p from GroundsPhotos p where p.grounds.gno=:gno")
  List<GroundsPhotos> findByMid(@Param("gno") Long gno);
}
