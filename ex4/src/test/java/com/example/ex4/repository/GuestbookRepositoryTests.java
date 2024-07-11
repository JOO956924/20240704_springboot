package com.example.ex4.repository;

import com.example.ex4.entity.Guestbook;
import com.example.ex4.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookRepositoryTests {

  @Autowired
  private GuestbookRepository guestbookRepository;

  @Test
  public void testRepository() {
    System.out.println(">>"+guestbookRepository.getClass().getName());
  }

  @Test
  public void insertDummies() {
    IntStream.rangeClosed(1, 300).forEach(i -> {
      Guestbook guestbook = Guestbook.builder()
          .title("Title... " + i)
          .content("Content... " + i)
          .writer("user"+(i%10))
          .build();
      System.out.println(guestbookRepository.save(guestbook));
    });
  }

  @Test
  public void testUpdate() {
    Optional<Guestbook> result = guestbookRepository.findById(300L);
    if (result.isPresent()) {
      Guestbook guestbook = result.get();
      guestbook.changeTitle("Changed Title...");
      guestbook.changeContent("Changed Content...");
      guestbookRepository.save(guestbook);
    }
  }

  // 단일 항목 검색
  @Test
  public void testQuery1() {
    // 페이징 객체 선언 :: 데이터가 많으니까 페이징 처리 함.
    Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
    // 동적 질의를 위한 .Guestbook 객체의 QGuestbook 준비
    QGuestbook qGuestbook = QGuestbook.guestbook;
    // 검색할 변수
    String keword = "1";
    // 검색을 실행할 객체
    BooleanBuilder builder = new BooleanBuilder();
    // 검색 조건 설정할 객체
    BooleanExpression expression = qGuestbook.title.contains(keword);
    // 검색 실행 준비 완료
    builder.and(expression);
    // 검색 실행 후 Page객체에 담음
    Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
    result.stream().forEach(new Consumer<Guestbook>() {
      @Override
      public void accept(Guestbook guestbook) {
        System.out.println(guestbook);
      }
    });
  }

  // 다중 항목 검색
  @Test
  public void testQuery2() {
    Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
    QGuestbook qGuestbook = QGuestbook.guestbook;
    String keword = "1";
    BooleanBuilder builder = new BooleanBuilder();
    BooleanExpression exTitle = qGuestbook.title.contains(keword);
    BooleanExpression exContent = qGuestbook.content.contains(keword);
    BooleanExpression exAll = exTitle.or(exContent);
    builder.and(exAll);
    builder.and(qGuestbook.gno.gt(0L)); //형식적이지만 추가해서 조건을 온전하게 함.
    Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
    result.stream().forEach(guestbook -> System.out.println(guestbook));
  }
}