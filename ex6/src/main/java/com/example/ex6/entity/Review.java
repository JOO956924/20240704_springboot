package com.example.ex6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"movie", "member"})
public class Review extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewnum;

  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  private int grade; //별점
  private String text; //한줄평

}
