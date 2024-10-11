package com.example.api.service;

import com.example.api.dto.GroundsReviewsDTO;
import com.example.api.entity.Grounds;
import com.example.api.entity.Members;
import com.example.api.entity.GroundsReviews;

import java.util.List;

public interface GroundsReviewsService {
  List<GroundsReviewsDTO> getListOfGrounds(Long gno);

  Long register(GroundsReviewsDTO groundsreviewsDTO);

  void modify(GroundsReviewsDTO groundsreviewsDTO);

  void remove(Long grno);

  public default GroundsReviews dtoToEntity(GroundsReviewsDTO groundsreviewsDTO) {
    GroundsReviews groundsreviews = GroundsReviews.builder()
        .grno(groundsreviewsDTO.getGrno())
        .grounds(Grounds.builder().gno(groundsreviewsDTO.getGno()).build())
        .members(Members.builder().mid(groundsreviewsDTO.getMid()).build())
        .Reservation(groundsreviewsDTO.getReservation())
        .GroundsTime(groundsreviewsDTO.getGroundsTime())
        .build();
    return groundsreviews;
  }

  default GroundsReviewsDTO entityToDto(GroundsReviews groundsreviews) {
    GroundsReviewsDTO groundsreviewsDTO = GroundsReviewsDTO.builder()
        .grno(groundsreviews.getGrno())
        .gno(groundsreviews.getGrounds().getGno())
        .mid(groundsreviews.getMembers().getMid())
        .email(groundsreviews.getMembers().getEmail())
        .Reservation(groundsreviews.getReservation())
        .GroundsTime(groundsreviews.getGroundsTime())
        .regDate(groundsreviews.getRegDate())
        .modDate(groundsreviews.getModDate())
        .build();
    return groundsreviewsDTO;
  }
}
