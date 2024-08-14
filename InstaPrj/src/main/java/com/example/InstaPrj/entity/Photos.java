package com.example.InstaPrj.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "feed")
public class Photos extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long pnum;

  private String uuid; //Universally Unique IDentifier
  private String imgName;
  private String path;
  @ManyToOne(fetch = FetchType.LAZY)
  private Feed feed;
}
