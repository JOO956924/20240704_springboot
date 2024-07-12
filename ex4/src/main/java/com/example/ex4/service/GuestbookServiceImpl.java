package com.example.ex4.service;

import com.example.ex4.dto.GuestbookDTO;
import com.example.ex4.dto.PageRequestDTO;
import com.example.ex4.dto.PageResultDTO;
import com.example.ex4.entity.Guestbook;
import com.example.ex4.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

  private final GuestbookRepository guestbookRepository;

  @Override
  public Long register(GuestbookDTO dto) {
    Guestbook guestbook = dtoToEntity(dto);
    guestbookRepository.save(guestbook);
    return guestbook.getGno();
  }

  @Override
  public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
    // 알고자하는 페이지(번호, 갯수, 정렬)
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

    // Page<Guestbook> 원하는 페이지의 목록
    Page<Guestbook> result = guestbookRepository.findAll(pageable);

    // 목록을 처리하기 위한 함수 정의
    Function<Guestbook, GuestbookDTO> fn = new Function<Guestbook, GuestbookDTO>() {
      @Override
      public GuestbookDTO apply(Guestbook guestbook) {
        return entityToDto(guestbook);
      }
    };
    // result는 요청페이지의 목록, fn은 result의  원소(Guestbook) 을 GuestbookDTO로 변환가능
    return new PageResultDTO<>(result, fn);
  }
}