package com.example.ex9.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
  private Long reviewnum;
  private Long fno; // Feed
  private Long mid; // ClubMember
  private String name;
  private String email;
  private int grade;
  private String text;
  private LocalDateTime regDate, modDate;
}

