package com.example.api.controller;

import com.example.api.dto.GroundsReviewsDTO;
import com.example.api.service.GroundsReviewsService;
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
@RequestMapping("/groundsreviews")
public class GroundsReviewsController {
  private final GroundsReviewsService groundsreviewsService;
  
  @GetMapping(value = "/{gno}/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GroundsReviewsDTO>> getList(@PathVariable("gno") Long gno) {
    log.info("gno: " + gno);
    List<GroundsReviewsDTO> reviewsDTOList = groundsreviewsService.getListOfGrounds(gno);
    return new ResponseEntity<>(reviewsDTOList, HttpStatus.OK);
  }

  @PostMapping("/{gno}")
  // @RequestBody : form이나, json 데이터를 전송받을 때
  // @RequestParam : 변수로 데이터를 전송받을 때
  public ResponseEntity<Long> register(@RequestBody GroundsReviewsDTO groundsreviewsDTO) {
    log.info(">>" + groundsreviewsDTO);
    Long grno = groundsreviewsService.register(groundsreviewsDTO);
    return new ResponseEntity<>(grno, HttpStatus.OK);
  }

  @PutMapping("/{gno}/{grno}")
  public ResponseEntity<Long> modify(@RequestBody GroundsReviewsDTO groundsreviewsDTO) {
    log.info(">>" + groundsreviewsDTO);
    groundsreviewsService.modify(groundsreviewsDTO);
    return new ResponseEntity<>(groundsreviewsDTO.getGrno(), HttpStatus.OK);
  }

  @DeleteMapping("/{gno}/{grno}")
  public ResponseEntity<Long> delete(@PathVariable Long grno) {
    log.info(">>" + grno);
    groundsreviewsService.remove(grno);
    return new ResponseEntity<>(grno, HttpStatus.OK);
  }
}
