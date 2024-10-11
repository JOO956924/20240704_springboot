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
  private String sports;
  private String matchStartDate;
  private String location;

  private String gtitle;
  public void changegTitle(String gtitle) {
    this.gtitle = gtitle;
  }


}
