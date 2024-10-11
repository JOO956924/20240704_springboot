package com.example.api.service;

import com.example.api.dto.GroundsDTO;
import com.example.api.dto.PageRequestDTO;
import com.example.api.dto.PageResultDTO;
import com.example.api.dto.GroundsPhotosDTO;
import com.example.api.entity.Grounds;
import com.example.api.entity.GroundsPhotos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface GroundsService {
  Long register(GroundsDTO groundsDTO);

  PageResultDTO<GroundsDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

  GroundsDTO getGrounds(Long gno);

  void modify(GroundsDTO groundsDTO);

  List<String> removeWithReviewsAndPhotos(Long gno);

  void removeUuid(String uuid);

  default Map<String, Object> dtoToEntity(GroundsDTO groundsDTO) {
    Map<String, Object> entityMap = new HashMap<>();
    Grounds grounds = Grounds.builder().gno(groundsDTO.getGno())
        .gtitle(groundsDTO.getGtitle()).build();
    entityMap.put("grounds", grounds);
    
    List<GroundsPhotosDTO> photosDTOList = groundsDTO.getGroundsPhotosDTOList();
    if (photosDTOList != null && photosDTOList.size() > 0) {
      List<GroundsPhotos> groundsphotosList = photosDTOList.stream().map(
          new Function<GroundsPhotosDTO, GroundsPhotos>() {
            @Override
            public GroundsPhotos apply(GroundsPhotosDTO photosDTO) {
              GroundsPhotos photos = GroundsPhotos.builder()
                  .path(photosDTO.getPath())
                  .photosName(photosDTO.getPhotosName())
                  .uuid(photosDTO.getUuid())
                  .grounds(grounds)
                  .build();
              return photos;
            }
          }
      ).collect(Collectors.toList());
      entityMap.put("groundsphotosList", groundsphotosList);
    }
    return entityMap;
  }

  default GroundsDTO entityToDto(Grounds grounds, List<GroundsPhotos> groundsphotosList
      , String location, String sports) {
    GroundsDTO groundsDTO = GroundsDTO.builder()
        .gno(grounds.getGno())
        .gtitle(grounds.getGtitle())
        .regDate(grounds.getRegDate())
        .modDate(grounds.getModDate())
        .build();
    List<GroundsPhotosDTO> photosDTOList = new ArrayList<>();
    if(groundsphotosList.toArray().length > 0 && groundsphotosList.toArray()[0] != null) {
      photosDTOList = groundsphotosList.stream().map(
          photos -> {
            GroundsPhotosDTO photosDTO = GroundsPhotosDTO.builder()
                .photosName(photos.getPhotosName())
                .path(photos.getPath())
                .uuid(photos.getUuid())
                .build();
            return photosDTO;
          }
      ).collect(Collectors.toList());
    }
    groundsDTO.setGroundsPhotosDTOList(photosDTOList);
    groundsDTO.setSports(sports);
    groundsDTO.setLocation(location);
    return groundsDTO;
  }
}

