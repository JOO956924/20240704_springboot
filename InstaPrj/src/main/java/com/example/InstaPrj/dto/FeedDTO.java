package com.example.InstaPrj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedDTO {
  private Long fno;
  private String title;
  @Builder.Default
  private List<PhotosDTO> imageDTOList = new ArrayList<>();
  private double avg;
  private Long reviewCnt;
  private LocalDateTime regDate;
  private LocalDateTime modDate;
}