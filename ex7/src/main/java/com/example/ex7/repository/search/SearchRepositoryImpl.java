package com.example.ex7.repository.search;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class SearchRepositoryImpl extends QuerydslRepositorySupport
    implements SearchRepository {

  public SearchRepositoryImpl(Class<?> domainClass) {
    super(domainClass);
  }

  @Override
  public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
    return null;
  }
}
