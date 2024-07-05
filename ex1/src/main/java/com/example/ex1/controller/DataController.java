package com.example.ex1.controller;

import com.example.ex1.dto.Foo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// CSR(Client Side Rendering):웹페이지를 제외한 데이터 전송 방식
@RestController
@RequestMapping("/data")
public class DataController {
  @GetMapping("/foo")
  public String getFoo() {
    return new Foo().toString();
  }
}
//메이븐은 컴파일과 라이브러리를 관리한다

/*
빈번한 커넥션 으로 인한 트래픽발생을 효과적 관리
 SQL Mapper
 ORM : 객체에서 매서드만 빼고 데이터베이스의 테이블 처럼 쓰기?
*/