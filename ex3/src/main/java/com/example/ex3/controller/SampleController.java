package com.example.ex3.controller;


import com.example.ex3.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
  //template engine인 thymeleaf를 적용한다면
  //templates 폴더의 파일들을 사용해야 하고,
  //이는 컨트롤러에서 반드시 중재해야만 페이지로 이동가능
  //컨트롤러에서 해당주소에 대한 중재가 없다면 404 에러 발생
  @GetMapping("/ex1")
  // void: 요청된 url이 렌더링주소페이지와 같다.
  public void ex1() {
    log.info("ex1.................");
  }

  @GetMapping("/selection")
  public void selection(Model model) {
    log.info("selection.................");
    SampleDTO sampleDTO = SampleDTO.builder()
        .sno(1L)
        .first("Kim")
        .last("JY")
        .regTime(LocalDateTime.now())
        .build();
    // model: 다음페이지에 전달할 객체를 전송
    model.addAttribute("sampleDTO",sampleDTO);
  }
}
