package com.example.ex6.service;

import com.example.ex6.dto.MovieDTO;
import com.example.ex6.dto.MovieImageDTO;
import com.example.ex6.entity.Movie;
import com.example.ex6.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface MovieService {
  Long register(MovieDTO movieDTO);

  //
  default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
    Map<String, Object> entityMap = new HashMap<>();
    Movie movie = Movie.builder().mno(movieDTO.getMno())
        .title(movieDTO.getTitle()).build();
    entityMap.put("movie", movie);
    List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
    if (imageDTOList != null && imageDTOList.size() > 0) {
      List<MovieImage> movieImageList = imageDTOList.stream().map(
          new Function<MovieImageDTO, MovieImage>() {
            @Override
            public MovieImage apply(MovieImageDTO movieImageDTO) {
              MovieImage movieImage = MovieImage.builder()
                  .path(movieImageDTO.getPath())
                  .imgName(movieImageDTO.getImgName())
                  .uuid(movieImageDTO.getUuid())
                  .movie(movie)
                  .build();
              return movieImage;
            }
          }
      ).collect(Collectors.toList());
      entityMap.put("movieImageList", movieImageList);
    }
    return entityMap;
  }

  default MovieDTO entityToDto(Movie movie, List<MovieImage> movieImageList
      , Double avg, int reviewCnt){
    MovieDTO movieDTO = MovieDTO.builder()
        .mno(movie.getMno())
        .title(movie.getTitle())
        .regDate(movie.getRegDate())
        .modDate(movie.getModDate())
        .build();
    List<MovieImageDTO> movieImageDTOList = movieImageList.stream().map(
        new Function<MovieImage, MovieImageDTO>() {
          @Override
          public MovieImageDTO apply(MovieImage movieImage) {
            MovieImageDTO movieImageDTO = MovieImageDTO.builder()
                .imgName(movieImage.getImgName())
                .path(movieImage.getPath())
                .uuid(movieImage.getUuid())
                .build();
            return movieImageDTO;
          }
        }
    ).collect(Collectors.toList());
    movieDTO.setAvg(avg);
    movieDTO.setReviewCnt(reviewCnt);
    return movieDTO;
  };
}