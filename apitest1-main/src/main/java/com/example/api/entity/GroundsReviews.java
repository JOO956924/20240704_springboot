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



  private LocalDateTime regDate;
  private LocalDateTime modDate;



}



