package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundsReviewsDTO {
  private Long grno;
  private Long gno; // Boards
  private Long mid; // Member
  private String email;
  private String Reservation;
  private String GroundsTime;
  private LocalDateTime regDate, modDate;
}
