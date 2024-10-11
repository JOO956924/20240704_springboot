package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "grounds")
public class GroundsPhotos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pnum;

  private String uuid; //Universally Unique IDentifier
  private String photosName;
  private String path;
  @ManyToOne(fetch = FetchType.LAZY)
  private Grounds grounds;
}

