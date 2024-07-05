package com.example.ex2.repository;

import com.example.ex2.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

  //쿼리메서드 (Query Method): 메서드 이름자체가 질의문이다.
  List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
  List<Memo> findByMemoTextContaining(String search);
  // List<Memo> findByMemoTextNotLike(String search);
  Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

  void deleteMemoByMnoLessThan(Long num);
}
