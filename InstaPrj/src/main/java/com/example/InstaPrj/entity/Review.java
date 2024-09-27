package com.example.InstaPrj.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"feed", "clubMember"})
public class Review extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewnum;

  @ManyToOne(fetch = FetchType.LAZY)
  private Feed feed;

  @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
  private ClubMember clubMember;

  private int grade;
  private String text;

  public void changeGrade(int grade) {this.grade = grade;}
  public void changeText(String text) {this.text = text;}
}
