package com.example.ex5.repository.search;

import com.example.ex5.entity.Board;
import com.example.ex5.entity.QBoard;
import com.example.ex5.entity.QMember;
import com.example.ex5.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport
    implements SearchBoardRepository {

  public SearchBoardRepositoryImpl() {
    super(Board.class);
  }

  @Override
  public Board search1() {
    log.info("search1.................");
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;

    JPQLQuery<Board> jpqlQuery = from(board);
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

    JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
    tuple.groupBy(board);

    log.info("==========================");
    log.info(tuple);
    log.info("==========================");

    List<Tuple> result = tuple.fetch();
    log.info("result: " + result);
    return null;
  }

  @Override
  public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
    log.info("searchPage...........");
    //1) q도메인 초기화
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;

    //2) q도메인 조인
    JPQLQuery<Board> jpqlQuery = from(board);
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

    //3) Tuple 생성: 조인의 결과 데이터를 tuple로 생성
    JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

    //4) 조건 생성
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    BooleanExpression expression = board.bno.gt(0L);
    booleanBuilder.and(expression);

    //5) 검색조건 파악
    if (type != null) {
      String[] typeArr = type.split("");
      BooleanBuilder conditionBuilder = new BooleanBuilder();
      for (String t : typeArr) {
        switch (t) {
          case "t":
            conditionBuilder.or(board.title.contains(keyword));
            break;
          case "w":
            conditionBuilder.or(member.email.contains(keyword));
            break;
          case "c":
            conditionBuilder.or(board.content.contains(keyword));
            break;
        }
      }
      booleanBuilder.and(conditionBuilder);
    }
    //6) 조인된 tuple에 조건절(booleanBuilder) 대입
    tuple.where(booleanBuilder);

    //7) 정렬조건 생성
    Sort sort = pageable.getSort();
    sort.stream().forEach(new Consumer<Sort.Order>() {
      @Override
      public void accept(Sort.Order order) {
        Order direction = order.isAscending() ? Order.ASC : Order.DESC;
        String prop = order.getProperty();
        PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
        tuple.orderBy(new OrderSpecifier<Comparable>(direction, orderByExpression.get(prop)));
      }
    });

    //8) 데이터 그룹 생성
    tuple.groupBy(board);

    //9) 원하는 데이터를 들고 오기 위해서 시작 위치 지정
    tuple.offset(pageable.getOffset());

    //10) offset으로부터 들고올 개수 지정
    tuple.limit(pageable.getPageSize());

    //11) 최종결과를 tuple의 fetch()함수를 통해서 컬렉션에 저장
    List<Tuple> result = tuple.fetch();

    //12) 최종결과의 갯수 출력
    long count = tuple.fetchCount();

    //13) 출력하고자 하는 Page객체를 위한 PageImpl 객체 생성
    return new PageImpl<>(
        result.stream().map(t->t.toArray()).collect(Collectors.toList())
        , pageable, count
    );
  }
}