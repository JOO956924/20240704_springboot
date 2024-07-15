package com.example.ex4.controller;

import com.example.ex4.dto.GuestbookDTO;
import com.example.ex4.dto.PageRequestDTO;
import com.example.ex4.service.GuestbookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

  private final GuestbookService guestbookService;

  @GetMapping({"", "/", "/list"})
  public String list(Model model, PageRequestDTO pageRequestDTO) {
    // 페이지에 대한 정보를 통해서 최종 PageResultDTO를 생성
    model.addAttribute("pageResultDTO",
        guestbookService.getList(pageRequestDTO));
    return "/guestbook/list";
  }

  @GetMapping("/register")
  public void registerGet() { log.info("register get.....");}

  @PostMapping("/register")
  public String registerPost(GuestbookDTO guestbookDTO, RedirectAttributes ra) {
    log.info("register post.........");
    Long gno = guestbookService.register(guestbookDTO);
    ra.addFlashAttribute("msg", gno);
    // redirect는 컨트롤러로 재전송한다는 의미
    return "redirect:/guestbook/list";
  }

  @GetMapping("/read")
  public void readGet(Long gno, int page, Model model) {
    log.info("read Get.......");
    GuestbookDTO guestbookDTO = guestbookService.read(gno);
    model.addAttribute("guestbookDTO", guestbookDTO);
    model.addAttribute("page",page);

  }
}