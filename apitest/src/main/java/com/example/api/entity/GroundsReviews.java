// 구장 상세 정보
package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"grounds", "members"})
public class GroundsReviews extends BasicEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long grno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Grounds grounds;

  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private String Reservation;
  private String GroundsTime; // 경기시간

  public void changeReservation(String Reservation) {this.Reservation = Reservation;}
  public void changeGroundsTime(String GroundsTime) {this.GroundsTime = GroundsTime;}
}
