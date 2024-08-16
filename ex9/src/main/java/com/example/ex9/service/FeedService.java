package com.example.ex9.service;

import com.example.ex9.dto.FeedDTO;
import com.example.ex9.dto.PageRequestDTO;
import com.example.ex9.dto.PageResultDTO;
import com.example.ex9.dto.PhotosDTO;
import com.example.ex9.entity.Feed;
import com.example.ex9.entity.Photos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface FeedService {
  Long register(FeedDTO feedDTO);

  PageResultDTO<FeedDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

  FeedDTO getFeed(Long fno);

  void modify(FeedDTO feedDTO);

  List<String> removeWithReviewsAndPhotos(Long fno);

  void removeUuid(String uuid);

  default Map<String, Object> dtoToEntity(FeedDTO feedDTO) {
    Map<String, Object> entityMap = new HashMap<>();
    Feed feed = Feed.builder().fno(feedDTO.getFno())
        .title(feedDTO.getTitle()).build();
    entityMap.put("feed", feed);
    List<PhotosDTO> imageDTOList = feedDTO.getImageDTOList();
    if (imageDTOList != null && imageDTOList.size() > 0) {
      List<Photos> photosList = imageDTOList.stream().map(
          new Function<PhotosDTO, Photos>() {
            @Override
            public Photos apply(PhotosDTO photosDTO) {
              Photos photos = Photos.builder()
                  .path(photosDTO.getPath())
                  .imgName(photosDTO.getImgName())
                  .uuid(photosDTO.getUuid())
                  .feed(feed)
                  .build();
              return photos;
            }
          }
      ).collect(Collectors.toList());
      entityMap.put("photosList", photosList);
    }
    return entityMap;
  }

  default FeedDTO entityToDto(Feed feed, List<Photos> photosList
      , Double avg, Long reviewCnt) {
    FeedDTO feedDTO = FeedDTO.builder()
        .fno(feed.getFno())
        .title(feed.getTitle())
        .regDate(feed.getRegDate())
        .modDate(feed.getModDate())
        .build();
    List<PhotosDTO> photosDTOList = new ArrayList<>();
    if(photosList.toArray().length > 0 && photosList.toArray()[0] != null) {
      photosDTOList = photosList.stream().map(
          photos -> {
            PhotosDTO photosDTO = PhotosDTO.builder()
                .imgName(photos.getImgName())
                .path(photos.getPath())
                .uuid(photos.getUuid())
                .build();
            return photosDTO;
          }
      ).collect(Collectors.toList());
    }
    feedDTO.setImageDTOList(photosDTOList);
    feedDTO.setAvg(avg);
    feedDTO.setReviewCnt(reviewCnt);
    return feedDTO;
  }
}
