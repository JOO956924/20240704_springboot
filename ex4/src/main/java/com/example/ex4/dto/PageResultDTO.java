package com.example.ex4.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data

public class PageResultDTO<DTO, EN> {

  private List<DTO> dtoList;// 한 페이지의 목록
  private int totalPage; // 총페이지수
  private int page; // 현재페이지위치
  private int size; // 한페이지의 총량
  private int start, end; // 시작페이지와 마지막페이지
  private boolean prev, next; // 다음 페이지와 이전 페이지 유무
  private List<Integer> pageList; // 페이지 목록에 대한 개수

  // 생성자를 통해서 페이징된 result를 각각의 원소에 대한 함수 적용
  public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
    // Page를 List로 변경 처리
    dtoList = result.stream().map(fn).collect(Collectors.toList());
    // 전체 페이지 개수 구하기
    totalPage = result.getTotalPages();
    makePageList(result.getPageable());
  }

  private void makePageList(Pageable pageable) {
    page = pageable.getPageNumber() + 1;
    size = pageable.getPageSize();
    int tempEnd = (int) Math.ceil((page / 10.0)) * 10;
    start = tempEnd - 9;
    prev = start > 1;
    end = totalPage > tempEnd ? tempEnd : totalPage;
    next = totalPage > tempEnd;
    pageList = // 페이지 번호 목록
        IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
  }
}
