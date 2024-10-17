package com.example.api.controller;

import com.example.api.dto.GroundsReviewsDTO;
import com.example.api.service.GroundsReviewService;
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
public class GroundsReviewsController {
  private final GroundsReviewService groundsReviewService;

  @GetMapping(value = "/{gno}/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GroundsReviewsDTO>> getList(@PathVariable("gno") Long gno) {
    log.info("gno: " + gno);
    List<GroundsReviewsDTO> groundsReviewsDTOList = groundsReviewService.getListOfGrounds(gno);
    return new ResponseEntity<>(groundsReviewsDTOList, HttpStatus.OK);
  }

  @PostMapping("/{gno}")
  // @RequestBody : form이나, json 데이터를 전송받을 때
  // @RequestParam : 변수로 데이터를 전송받을 때
  public ResponseEntity<Long> register(@RequestBody GroundsReviewsDTO groundsReviewsDTO) {
    log.info(">>" + groundsReviewsDTO);
    Long greviewsnum = groundsReviewService.register(groundsReviewsDTO);
    return new ResponseEntity<>(greviewsnum, HttpStatus.OK);
  }

  @PutMapping("/{gno}/{greviewsnum}")
  public ResponseEntity<Long> modify(@RequestBody GroundsReviewsDTO groundsReviewsDTO) {
    log.info(">>" + groundsReviewsDTO);
    groundsReviewService.modify(groundsReviewsDTO);
    return new ResponseEntity<>(groundsReviewsDTO.getGreviewsnum(), HttpStatus.OK);
  }

  @DeleteMapping("/{gno}/{greviewsnum}")
  public ResponseEntity<Long> delete(@PathVariable Long greviewsnum) {
    log.info(">>" + greviewsnum);
    groundsReviewService.remove(greviewsnum);
    return new ResponseEntity<>(greviewsnum, HttpStatus.OK);
  }

}
