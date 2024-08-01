package com.example.ex6.service;

import com.example.ex6.dto.MovieDTO;
import com.example.ex6.entity.Movie;
import com.example.ex6.entity.MovieImage;
import com.example.ex6.repository.MovieImageRepository;
import com.example.ex6.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
  private final MovieRepository movieRepository;
  private final MovieImageRepository movieImageRepository;

  @Override
  public Long register(MovieDTO movieDTO) {
    Map<String, Object> entityMap = dtoToEntity(movieDTO);
    Movie movie = (Movie) entityMap.get("movie");
    List<MovieImage> movieImageList =
        (List<MovieImage>) entityMap.get("movieImageList");
    movieRepository.save(movie);
    movieImageList.forEach(new Consumer<MovieImage>() {
      @Override
      public void accept(MovieImage movieImage) {
        movieImageRepository.save(movieImage);
      }
    });
    return movie.getMno();
  }
}
