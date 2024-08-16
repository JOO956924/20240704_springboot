package com.example.ex9.service;

import com.example.ex9.dto.FeedDTO;
import com.example.ex9.dto.PageRequestDTO;
import com.example.ex9.dto.PageResultDTO;
import com.example.ex9.entity.Feed;
import com.example.ex9.entity.Photos;
import com.example.ex9.repository.FeedRepository;
import com.example.ex9.repository.PhotosRepository;
import com.example.ex9.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
  private final FeedRepository feedRepository;
  private final PhotosRepository photosRepository;
  private final ReviewRepository reviewRepository;

  @Override
  public Long register(FeedDTO feedDTO) {
    Map<String, Object> entityMap = dtoToEntity(feedDTO);
    Feed feed = (Feed) entityMap.get("feed");
    List<Photos> photosList =
        (List<Photos>) entityMap.get("photosList");
    feedRepository.save(feed);
    if(photosList != null) {
      photosList.forEach(new Consumer<Photos>() {
        @Override
        public void accept(Photos photos) {
          photosRepository.save(photos);
        }
      });
    }
    return feed.getFno();
  }

  @Override
  public PageResultDTO<FeedDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("fno").descending());
    // Page<Feed> result = feedRepository.findAll(pageable);
//    Page<Object[]> result = feedRepository.getListPageImg(pageable);
    Page<Object[]> result = feedRepository.searchPage(pageRequestDTO.getType(),
        pageRequestDTO.getKeyword(),
        pageable);
    Function<Object[], FeedDTO> fn = objects -> entityToDto(
        (Feed) objects[0],
        (List<Photos>) (Arrays.asList((Photos)objects[1])),
        (Double) objects[2],
        (Long) objects[3]
    );
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public FeedDTO getFeed(Long fno) {
    List<Object[]> result = feedRepository.getFeedWithAll(fno);
    Feed feed = (Feed) result.get(0)[0];
    List<Photos> photoss = new ArrayList<>();
    result.forEach(objects -> photoss.add((Photos) objects[1]));
    Double avg = (Double) result.get(0)[2];
    Long reviewCnt = (Long) result.get(0)[3];

    return entityToDto(feed, photoss, avg, reviewCnt);
  }

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @Transactional
  @Override
  public void modify(FeedDTO feedDTO) {
    Optional<Feed> result = feedRepository.findById(feedDTO.getFno());
    if (result.isPresent()) {
      Map<String, Object> entityMap = dtoToEntity(feedDTO);
      Feed feed = (Feed) entityMap.get("feed");
      feed.changeTitle(feedDTO.getTitle());
      feedRepository.save(feed);
      // photosList :: 수정창에서 이미지 수정할 게 있는 경우의 목록
      List<Photos> newPhotosList =
          (List<Photos>) entityMap.get("photosList");
      List<Photos> oldPhotosList =
          photosRepository.findByFno(feed.getFno());
      if(newPhotosList == null) {
        // 수정창에서 이미지 모두를 지웠을 때
        photosRepository.deleteByFno(feed.getFno());
        for (int i = 0; i < oldPhotosList.size(); i++) {
          Photos oldPhotos = oldPhotosList.get(i);
          String fileName = oldPhotos.getPath() + File.separator
              + oldPhotos.getUuid() + "_" + oldPhotos.getImgName();
          deleteFile(fileName);
        }
      } else { // newPhotosList에 일부 변화 발생
        newPhotosList.forEach(photos -> {
          boolean result1 = false;
          for (int i = 0; i < oldPhotosList.size(); i++) {
            result1 = oldPhotosList.get(i).getUuid().equals(photos.getUuid());
            if(result1) break;
          }
          if(!result1) photosRepository.save(photos);
        });
        oldPhotosList.forEach(oldPhotos -> {
          boolean result1 = false;
          for (int i = 0; i < newPhotosList.size(); i++) {
            result1 = newPhotosList.get(i).getUuid().equals(oldPhotos.getUuid());
            if(result1) break;
          }
          if(!result1) {
            photosRepository.deleteByUuid(oldPhotos.getUuid());
            String fileName = oldPhotos.getPath() + File.separator
                + oldPhotos.getUuid() + "_" + oldPhotos.getImgName();
            deleteFile(fileName);
          }
        });
      }
    }
  }

  private void deleteFile(String fileName) {
    // 실제 파일도 지우기
    String searchFilename = null;
    try {
      searchFilename = URLDecoder.decode(fileName, "UTF-8");
      File file = new File(uploadPath + File.separator + searchFilename);
      file.delete();
      new File(file.getParent(), "s_" + file.getName()).delete();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Transactional
  @Override
  public List<String> removeWithReviewsAndPhotos(Long fno) {
    List<Photos> list = photosRepository.findByFno(fno);
    List<String> result = new ArrayList<>();
    list.forEach(new Consumer<Photos>() {
      @Override
      public void accept(Photos t) {
        result.add(t.getPath() + File.separator + t.getUuid() + "_" + t.getImgName());
      }
    });
    photosRepository.deleteByFno(fno);
    reviewRepository.deleteByFno(fno);
    feedRepository.deleteById(fno);
    return result;
  }

  @Override
  public void removeUuid(String uuid) {
    log.info("deleteImage...... uuid: " + uuid);
    photosRepository.deleteByUuid(uuid);
  }
}
