package com.example.api.service;

import com.example.api.dto.BoardsDTO;
import com.example.api.dto.PageRequestDTO;
import com.example.api.dto.PageResultDTO;
import com.example.api.dto.PhotosDTO;
import com.example.api.entity.Boards;
import com.example.api.entity.Photos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface BoardsService {
  Long register(BoardsDTO boardsDTO);

  PageResultDTO<BoardsDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

  BoardsDTO getBoards(Long bno);

  void modify(BoardsDTO boardsDTO);

  List<String> removeWithReviewsAndPhotos(Long bno);

  void removeUuid(String uuid);

  default Map<String, Object> dtoToEntity(BoardsDTO boardsDTO) {
    Map<String, Object> entityMap = new HashMap<>();
    Boards boards = Boards.builder().bno(boardsDTO.getBno())
        .title(boardsDTO.getTitle()).build();
    entityMap.put("boards", boards);
    List<PhotosDTO> photosDTOList = boardsDTO.getPhotosDTOList();
    if (photosDTOList != null && photosDTOList.size() > 0) {
      List<Photos> photosList = photosDTOList.stream().map(
          new Function<PhotosDTO, Photos>() {
            @Override
            public Photos apply(PhotosDTO photosDTO) {
              Photos photos = Photos.builder()
                  .path(photosDTO.getPath())
                  .photosName(photosDTO.getPhotosName())
                  .uuid(photosDTO.getUuid())
                  .boards(boards)
                  .build();
              return photos;
            }
          }
      ).collect(Collectors.toList());
      entityMap.put("photosList", photosList);
    }
    return entityMap;
  }

  default BoardsDTO entityToDto(Boards boards, List<Photos> photosList
      , Long likes, Long reviewsCnt) {
    BoardsDTO boardsDTO = BoardsDTO.builder()
        .bno(boards.getBno())
        .title(boards.getTitle())
        .regDate(boards.getRegDate())
        .modDate(boards.getModDate())
        .build();
    List<PhotosDTO> photosDTOList = new ArrayList<>();
    if(photosList.toArray().length > 0 && photosList.toArray()[0] != null) {
      photosDTOList = photosList.stream().map(
          photos -> {
            PhotosDTO photosDTO = PhotosDTO.builder()
                .photosName(photos.getPhotosName())
                .path(photos.getPath())
                .uuid(photos.getUuid())
                .build();
            return photosDTO;
          }
      ).collect(Collectors.toList());
    }
    boardsDTO.setPhotosDTOList(photosDTOList);
    boardsDTO.setLikes(likes);
    boardsDTO.setReviewsCnt(reviewsCnt);
    return boardsDTO;
  }
}
