package com.example.exJSP.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class SampleController {
  @RequestMapping({"","/","/main"})
  public String main() {
    return "/index";
  }
}
