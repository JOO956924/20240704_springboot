// 구장 정보
package com.example.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Grounds extends BasicEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gno;

  private String gtitle;
  private String location;
  private String sports;
  private int price;
  private int maxpeople; // 최대 신청 가능한 인원수   ex 18
  private int nowpeople; // 현재 신청 한 인원수      ex 18
  private String groundsTime; // 경기 시작 시간

  public void changeTitle(String gtitle) {this.gtitle = gtitle;}
  public void changenowpeople(int nowpeople) {this.nowpeople = nowpeople;}
}