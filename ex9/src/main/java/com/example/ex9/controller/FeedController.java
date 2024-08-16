package com.example.ex9.controller;

import com.example.ex9.dto.FeedDTO;
import com.example.ex9.dto.PageRequestDTO;
import com.example.ex9.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
  private final FeedService feedService;

  @GetMapping("/register")
  public void register() {
  }

  @PostMapping("/register")
  public String registerPost(FeedDTO feedDTO, RedirectAttributes ra) {
    Long fno = feedService.register(feedDTO);
    ra.addFlashAttribute("msg", fno);
    return "redirect:/feed/list";
  }

  @GetMapping({"","/","/list"})
  public String list(PageRequestDTO pageRequestDTO, Model model) {
    model.addAttribute("pageResultDTO", feedService.getList(pageRequestDTO));
    return "/feed/list";
  }

  @GetMapping({"/read", "/modify"})
  public void getFeed(Long fno, PageRequestDTO pageRequestDTO, Model model) {
    FeedDTO feedDTO = feedService.getFeed(fno);
    typeKeywordInit(pageRequestDTO);
    model.addAttribute("feedDTO", feedDTO);
  }
  @PostMapping("/modify")
  public String modify(FeedDTO dto, RedirectAttributes ra, PageRequestDTO pageRequestDTO){
    log.info("modify post... dto: " + dto);
    feedService.modify(dto);
    typeKeywordInit(pageRequestDTO);
    ra.addFlashAttribute("msg", dto.getFno() + " 수정");
    ra.addAttribute("fno", dto.getFno());
    ra.addAttribute("page", pageRequestDTO.getPage());
    ra.addAttribute("type", pageRequestDTO.getType());
    ra.addAttribute("keyword", pageRequestDTO.getKeyword());
    return "redirect:/feed/read";
  }

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @PostMapping("/remove")
  public String remove(Long fno, RedirectAttributes ra, PageRequestDTO pageRequestDTO){
    log.info("remove post... fno: " + fno);
    List<String> result = feedService.removeWithReviewsAndPhotos(fno);
    log.info("result>>"+result);
    result.forEach(fileName -> {
      try {
        log.info("removeFile............"+fileName);
        String srcFileName = URLDecoder.decode(fileName, "UTF-8");
        File file = new File(uploadPath + File.separator + srcFileName);
        file.delete();
        File thumb = new File(file.getParent(),"s_"+file.getName());
        thumb.delete();
      } catch (Exception e) {
        log.info("remove file : "+e.getMessage());
      }
    });
    if(feedService.getList(pageRequestDTO).getDtoList().size() == 0 && pageRequestDTO.getPage() != 1) {
      pageRequestDTO.setPage(pageRequestDTO.getPage()-1);
    }
    typeKeywordInit(pageRequestDTO);
    ra.addFlashAttribute("msg", fno + " 삭제");
    ra.addAttribute("page", pageRequestDTO.getPage());
    ra.addAttribute("type", pageRequestDTO.getType());
    ra.addAttribute("keyword", pageRequestDTO.getKeyword());
    return "redirect:/feed/list";
  }
  private void typeKeywordInit(PageRequestDTO pageRequestDTO){
    if (pageRequestDTO.getType().equals("null")) pageRequestDTO.setType("");
    if (pageRequestDTO.getKeyword().equals("null")) pageRequestDTO.setKeyword("");
  }
}