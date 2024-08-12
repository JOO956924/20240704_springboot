package com.example.ex8.controller;

import com.example.ex8.dto.NoteDTO;
import com.example.ex8.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
  private final NoteService noteService;

  @PostMapping({"","/"})
  public ResponseEntity<Long> register(NoteDTO noteDTO) {
    log.info("noteDTO: "+noteDTO);
    Long num = noteService.register(noteDTO);
    return new ResponseEntity<>(num, HttpStatus.OK);
  }
}