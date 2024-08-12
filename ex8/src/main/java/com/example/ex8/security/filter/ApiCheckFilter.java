package com.example.ex8.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {
  private String[] pattern;
  private AntPathMatcher antPathMatcher;

  public ApiCheckFilter(String[] pattern) {
    this.pattern = pattern;
    antPathMatcher = new AntPathMatcher();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {

    log.info("request.getRequestURI()" + request.getRequestURI());
    log.info("request.getContextPath()" + request.getContextPath());
    log.info("REQUEST match: "+ request.getContextPath(), request.getRequestURI());

    boolean check = false;
    for (int i = 0; i < pattern.length; i++) {
      log.info(">>"+request.getContextPath() + pattern[i] + "/" + request.getRequestURI());
      if (antPathMatcher.match(request.getContextPath() + pattern[i],
          request.getRequestURI())) {
        check = true;
        break;
      }
    }
    if (check) {
      log.info("check :"+check);
      
    }
    filterChain.doFilter(request, response);
  }
}
