package com.example.ex6.service;

import com.example.ex6.dto.MovieDTO;
import com.example.ex6.dto.PageRequestDTO;
import com.example.ex6.dto.PageResultDTO;
import com.example.ex6.entity.Movie;
import com.example.ex6.entity.MovieImage;
import com.example.ex6.repository.MovieImageRepository;
import com.example.ex6.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

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

  @Override
  public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
    // Page<Movie> result = movieRepository.findAll(pageable);
    Page<Object[]> result = movieRepository.getListPageImg(pageable);
    Function<Object[], MovieDTO> fn = objects -> entityToDto(
        (Movie) objects[0],
        (List<MovieImage>) (Arrays.asList((MovieImage)objects[1])),
        (Double) objects[2],
        (Long) objects[3]
    );
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public MovieDTO getMovie(Long mno) {
    List<Object[]> result = movieRepository.getMovieWithAll(mno);
    Movie movie = (Movie) result.get(0)[0];
    List<MovieImage> movieImageList = (List<MovieImage>) result.get(0)[1];
    Double avg = (Double) result.get(0)[2];
    Long MovieCount = (Long) result.get(0)[3];
    return null;
  }
}
