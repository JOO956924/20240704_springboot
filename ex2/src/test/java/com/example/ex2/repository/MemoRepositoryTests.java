package com.example.ex2.repository;

import com.example.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

//JPA
@SpringBootTest
class MemoRepositoryTests {
  @Autowired
  MemoRepository memoRepository;

  @Test
  public void testClass() {
    System.out.println(">>>" + memoRepository.getClass().getName());
  }

  @Test
  public void testInsertDummies() {
    IntStream.rangeClosed(1, 100).forEach(new IntConsumer() {
      @Override
      public void accept(int value) {
        Memo memo = Memo.builder()
            .memoText("Simple memo... " + value)
            .build();
        memoRepository.save(memo);
      }
    });
  }

  @Test
  public void testSelect() {
    Long mno = 100L;
    Optional<Memo> result = memoRepository.findById(mno);
    if (result.isPresent()) {
      Memo memo = result.get();
      System.out.println(memo);
    }
  }

  @Test
  public void testUpdate() {

    Memo memo = Memo.builder().memoText("update 100").build();
    memoRepository.save(memo);

    // 주의해야되는 update(컬럼 mno,memoText 외의 더 많은 컬럼이 있는 경우는 먼저 불러와야함.
    Long mno = 100L;
    Optional<Memo> result = memoRepository.findById(mno);
    if (result.isPresent()) {
      Memo memo3 = Memo.builder().mno(result.get().getMno()).memoText("update 100").build();
      memoRepository.save(memo3);
    }
  }

  @Test
  public void testDelete() {
    Long mno = 100L;
    memoRepository.deleteById(mno);

  }

  @Test
  public void testPageDefault() {
    // 페이지를 지정할수 있는 객체
    Pageable pageable = PageRequest.of(0, 10);

    // Paging 처리 후 결과를 담기 위한 객체 Page 사용
    Page<Memo> result = memoRepository.findAll(pageable);
    System.out.println(">>> " + result);
    System.out.println("===========================================");
    System.out.println("Total Page: " + result.getTotalPages());
    System.out.println("Total Count: " + result.getTotalElements());
    System.out.println("Page Number: " + result.getNumber());
    System.out.println("Page Size: " + result.getSize());
    System.out.println("has next page: " + result.hasNext());
    System.out.println("has previous page: " + result.hasPrevious());
    System.out.println("first page: " + result.isFirst());
    System.out.println("last page: " + result.isLast());

    Sort sort = Sort.by("mno").descending();
    pageable = PageRequest.of(0, 10, sort);
    result = memoRepository.findAll(pageable);

    for (Memo memo : result.getContent()) {
      System.out.println(memo);
    }
  }

  @Test
  public void testQueryMethod() {
    List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
    for (Memo memo : list) {
      System.out.println(memo);
    }
  }
  @Test
  public void testQueryMethod2() {
    List<Memo> list = memoRepository.findByMemoTextContaining("7");
    for (Memo memo : list) {
      System.out.println(memo);
    }
  }

  @Test
  public void testQueryMethodPageable() {
    Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
    Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);
    result.get().forEach(memo -> System.out.println(memo));

  }


  // 쿼리메서드에 DeleteBy가 있는 경우 검색후 삭제이기 때문
  @Transactional
  @Commit
  @Test
  public void testDeleteQueryMethod() {
    memoRepository.deleteMemoByMnoLessThan(10L);
  }
}