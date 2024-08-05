package com.example.ex6.controller;

import com.example.ex6.dto.MovieDTO;
import com.example.ex6.dto.PageRequestDTO;
import com.example.ex6.service.MovieService;
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
import java.util.function.Consumer;

@Controller
@Log4j2
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
  private final MovieService movieService;

  @GetMapping("/register")
  public void register() {
  }

  @PostMapping("/register")
  public String registerPost(MovieDTO movieDTO, RedirectAttributes ra) {
    Long mno = movieService.register(movieDTO);
    ra.addFlashAttribute("msg", mno);
    return "redirect:/movie/list";
  }

  @GetMapping({"","/","/list"})
  public String list(PageRequestDTO pageRequestDTO, Model model) {
    model.addAttribute("pageResultDTO", movieService.getList(pageRequestDTO));
    return "/movie/list";
  }

  @GetMapping({"/read", "/modify"})
  public void getMovie(Long mno, PageRequestDTO pageRequestDTO, Model model) {
    MovieDTO movieDTO = movieService.getMovie(mno);
    model.addAttribute("movieDTO", movieDTO);
  }
  @PostMapping("/modify")
  public String modify(MovieDTO dto, RedirectAttributes ra, PageRequestDTO req){
    log.info("modify post... dto: " + dto);
    movieService.modify(dto);
    ra.addFlashAttribute("msg", dto.getMno() + " 수정");
    ra.addAttribute("mno", dto.getMno());
    ra.addAttribute("page", req.getPage());
    ra.addAttribute("type", req.getType());
    ra.addAttribute("keyword", req.getKeyword());
    return "redirect:/movie/read";
  }

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @PostMapping("/remove")
  public String remove(Long mno, RedirectAttributes ra, PageRequestDTO req){
    log.info("remove post... mno: " + mno);
    List<String> result = movieService.removeWithReviewsAndMovieImages(mno);
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
    if(movieService.getList(req).getDtoList().size() == 0 && req.getPage() != 1) {
      req.setPage(req.getPage()-1);
    }
    ra.addFlashAttribute("msg", mno + " 삭제");
    ra.addAttribute("page", req.getPage());
    ra.addAttribute("type", req.getType());
    ra.addAttribute("keyword", req.getKeyword());
    return "redirect:/movie/list";
  }
}
