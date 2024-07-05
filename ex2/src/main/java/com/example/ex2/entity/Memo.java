package com.example.ex2.entity;


import jakarta.persistence.*;
import lombok.*;


// null != null;
@Entity
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder // 사용할 때 AllArgsConstructor,NoArgsConstructor 같이 써야 컴파일 에러가 발생하지 않음
@AllArgsConstructor
@NoArgsConstructor

public class Memo {

  @Id
  // primary key를 자동으로 생성할 때 사용
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long mno;

  @Column(length = 200, nullable = false)
  private String memoText;



}
