package com.example.ex6.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.ex6.entity.Movie;

public interface SearchRepository {
//  Movie search1();

  Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
