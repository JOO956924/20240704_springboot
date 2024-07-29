package com.example.ex6.repository;

import com.example.ex6.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

//  select m.mno, avg(coalesce(r.grade,0)), count(r.reviewnum)
//  from movie m left outer join review r on m.mno = r.movie_mno
//  group by m.mno;
  @Query("select m, avg(coalesce(r.grade, 0)), count(distinct r) " +
      "from Movie m left outer join Review r on r.movie=m group by m ")
  Page<Object[]> getListPage(Pageable pageable);


  // 아래와 같은 경우 mi를 찾기 위해서 review 카운트 만큼 반복횟수도 늘어나는 문제점
  // mi의 inum이 가장 낮은 이미지번호가 출력된다.
  @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) " +
      "from Movie m left outer join MovieImage mi on mi.movie = m " +
      "left outer join Review r on r.movie=m group by m ")
  Page<Object[]> getListPageImg(Pageable pageable);
}
