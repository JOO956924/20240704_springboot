package com.example.ex5.repository.search;

import com.example.ex5.entity.Board;
import com.example.ex5.entity.QBoard;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport
    implements SearchBoardRepository {

  public SearchBoardRepositoryImpl() {
    super(Board.class);
  }

  @Override
  public Board search1() {
    log.info("search1.........");

    QBoard qBoard = QBoard.board;
    JPQLQuery<Board> jpqlQuery = from(qBoard);

    return null;
  }
}