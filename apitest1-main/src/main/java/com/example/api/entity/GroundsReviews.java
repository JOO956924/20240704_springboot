package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"grounds", "members"})
public class GroundsReviews {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long grno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Grounds grounds;

  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private int maxpeople; // 최대 신청 가능한 인원수   ex 18
  private int nowpeople; // 현재 신청 한 인원수      ex 18

  private LocalDateTime regDate;
  private LocalDateTime modDate;

  public void changenowpeople(int nowpeople) {this.nowpeople = nowpeople;}

}



