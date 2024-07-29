package com.example.ex6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "movie")
public class MovieImage extends BasicEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long inum;

  private String uuid; //Universally Unique IDentifier
  private String imgName;
  private String path;
  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;
}
