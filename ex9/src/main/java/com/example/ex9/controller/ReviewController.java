package com.example.ex9.controller;

import com.example.ex9.dto.ReviewDTO;
import com.example.ex9.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping(value = "/{fno}/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("fno") Long fno) {
    log.info("fno: " + fno);
    List<ReviewDTO> reviewDTOList = reviewService.getListOfFeed(fno);
    return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
  }

  @PostMapping("/{fno}")
  // @RequestBody : form이나, json 데이터를 전송받을 때
  // @RequestParam : 변수로 데이터를 전송받을 때
  public ResponseEntity<Long> register(@RequestBody ReviewDTO reviewDTO) {
    log.info(">>" + reviewDTO);
    Long reviewnum = reviewService.register(reviewDTO);
    return new ResponseEntity<>(reviewnum, HttpStatus.OK);
  }

  @PutMapping("/{fno}/{reviewnum}")
  public ResponseEntity<Long> modify(@RequestBody ReviewDTO reviewDTO) {
    log.info(">>" + reviewDTO);
    reviewService.modify(reviewDTO);
    return new ResponseEntity<>(reviewDTO.getReviewnum(), HttpStatus.OK);
  }

  @DeleteMapping("/{fno}/{reviewnum}")
  public ResponseEntity<Long> delete(@PathVariable Long reviewnum) {
    log.info(">>" + reviewnum);
    reviewService.remove(reviewnum);
    return new ResponseEntity<>(reviewnum, HttpStatus.OK);
  }

}
