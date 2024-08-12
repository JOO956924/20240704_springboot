package com.example.ex8.service;

import com.example.ex8.dto.NoteDTO;
import com.example.ex8.entity.ClubMember;
import com.example.ex8.entity.Note;

import java.util.List;

public interface NoteService {
  Long register(NoteDTO noteDTO);

  NoteDTO get(Long num);

  void modify(NoteDTO noteDTO);

  void remove(Long num);

  List<NoteDTO> getAllWithWriter(String writerEmail);

  default Note dtoToEntity(NoteDTO noteDTO) {
    Note note = Note.builder()
        .num(noteDTO.getNum())
        .title(noteDTO.getTitle())
        .content(noteDTO.getContent())
        .writer(ClubMember.builder().cno(noteDTO.getWriterCno()).build())
        .build();
    return note;
  }

  default NoteDTO entityToDTO(Note note) {
    NoteDTO noteDTO = NoteDTO.builder()
        .num(note.getNum())
        .title(note.getTitle())
        .content(note.getContent())
        .writerCno(note.getWriter().getCno())
        .regDate(note.getRegDate())
        .modDate(note.getModDate())
        .build();
    return noteDTO;
  }
}