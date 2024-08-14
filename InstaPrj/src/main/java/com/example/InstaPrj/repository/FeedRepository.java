package com.example.InstaPrj.repository;

import com.example.InstaPrj.entity.Feed;
import com.example.InstaPrj.repository.search.SearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long>, SearchRepository {


  @Query("select f, avg(coalesce(r.grade, 0)), count(distinct r) " +
      "from Feed f left outer join Review r on r.feed=f group by f ")
  Page<Object[]> getListPage(Pageable pageable);

  @Query("select f, p, avg(coalesce(r.grade, 0)), count(distinct r) from Feed f " +
      "left outer join Photos p on p.feed = f " +
      "left outer join Review     r  on r.feed  = f group by f ")
  Page<Object[]> getListPageImg(Pageable pageable);

  @Query("select f,max(p),avg(coalesce(r.grade, 0)),count(distinct r) from Feed f " +
      "left outer join Photos p on p.feed = f " +
      "left outer join Review     r  on r.feed  = f group by f ")
  Page<Object[]> getListPageMaxImg(Pageable pageable);

  @Query(value = "select f.fno, p.pnum, p.img_name, " +
      "avg(coalesce(r.grade, 0)), count(r.reviewnum) " +
      "from db7.photos p left outer join db7.feed f on f.fno=p.feed_fno " +
      "left outer join db7.review r on f.fno=r.feed_fno " +
      "where p.pnum = " +
      "(select max(pnum) from db7.photos p2 where p2.feed_fno=f.fno) " +
      "group by f.fno ", nativeQuery = true)
  Page<Object[]> getListPageImgNative(Pageable pageable);

  // JPQL
  @Query("select f, p, avg(coalesce(r.grade, 0)), count(distinct r) from Feed f " +
      "left outer join Photos p on p.feed = f " +
      "left outer join Review     r  on r.feed  = f " +
      "where pnum = (select max(p2.pnum) from Photos p2 where p2.feed=f) " +
      "group by f ")
  Page<Object[]> getListPageImgJPQL(Pageable pageable);

  @Query("select feed, max(p.pnum) from Photos p group by feed")
  Page<Object[]> getMaxQuery(Pageable pageable);

  @Query("select f, p, avg(coalesce(r.grade, 0)), count(r) " +
      "from Feed m left outer join Photos p on p.feed=f " +
      "left outer join Review r on r.feed = f " +
      "where f.fno = :fno group by p ")
  List<Object[]> getMovieWithAll(Long mno); //특정 영화 조회

}
