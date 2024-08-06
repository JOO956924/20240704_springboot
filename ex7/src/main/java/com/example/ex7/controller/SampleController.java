package com.example.ex7.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

  @GetMapping("/all")
  // 모든 이 접근
  public void exAll() {
    log.info("/all");
  }
  @GetMapping("/Member")
  // 로그인 사용자 접근
  public void exMember() {
    log.info("/Member");
  }
  @GetMapping("/admin")
  // 로그인 사용자중 관리자만 발급
  public void admin() {
    log.info("/admin");
  }
}
