package com.example.api.service;

import com.example.api.dto.GroundsReviewsDTO;
import com.example.api.entity.Grounds;
import com.example.api.entity.GroundsReviews;
import com.example.api.repository.GroundsReviwsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class GroundsReviewsServiceIpml implements GroundsReviewsService{
  private final GroundsReviwsRepository groundsreviewsRepository;

  @Override
  public List<GroundsReviewsDTO> getListOfGrounds (Long gno) {
    List<GroundsReviews> result = groundsreviewsRepository.findByGrounds(
        Grounds.builder().gno(gno).build());
    return result.stream().map(groundsreviews -> entityToDto(groundsreviews)).collect(Collectors.toList());
  }

  @Override
  public Long register(GroundsReviewsDTO groundsreviewsDTO) {
    log.info("groundsreviewsDTO >> ", groundsreviewsDTO);
    GroundsReviews groundsreviews = dtoToEntity(groundsreviewsDTO);
    groundsreviewsRepository.save(groundsreviews);
    return groundsreviews.getGrno();
  }

  @Override
  public void modify(GroundsReviewsDTO groundsreviewsDTO) {
    Optional<GroundsReviews> result = groundsreviewsRepository.findById(groundsreviewsDTO.getGrno());
    if (result.isPresent()) {
      GroundsReviews groundsreviews = result.get();
      groundsreviews.changeReservation(groundsreviewsDTO.getReservation());
      groundsreviews.changeGroundsTime(groundsreviewsDTO.getGroundsTime());
      groundsreviewsRepository.save(groundsreviews);
    }
  }

  @Override
  public void remove(Long grno) {
    groundsreviewsRepository.deleteById(grno);
  }
}
