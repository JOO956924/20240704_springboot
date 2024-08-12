package com.example.ex8.service;

import com.example.ex8.dto.NoteDTO;
import com.example.ex8.entity.Note;
import com.example.ex8.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  public final NoteRepository noteRepository;

  @Override
  public Long register(NoteDTO noteDTO) {
    Note note = dtoToEntity(noteDTO);
    noteRepository.save(note);
    return note.getNum();
  }

  @Override
  public NoteDTO get(Long num) {

    Objects result = noteRepository.getWithWriter();
    return entityToDTO()
  }

  @Override
  public void modify(NoteDTO noteDTO) {

  }

  @Override
  public void remove(Long num) {

  }

  @Override
  public List<NoteDTO> getAllWithWriter(String writerEmail) {
    return List.of();
  }
}
