package com.example.api.service;

import com.example.api.dto.BoardsDTO;
import com.example.api.dto.PageRequestDTO;
import com.example.api.dto.PageResultDTO;
import com.example.api.entity.Boards;
import com.example.api.entity.Photos;
import com.example.api.repository.BoardsRepository;
import com.example.api.repository.PhotosRepository;
import com.example.api.repository.ReviewsRepository;
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
public class BoardsServiceImpl implements BoardsService {
  private final BoardsRepository boardsRepository;
  private final PhotosRepository photosRepository;
  private final ReviewsRepository reviewsRepository;

  @Override
  public Long register(BoardsDTO boardsDTO) {
    Map<String, Object> entityMap = dtoToEntity(boardsDTO);
    Boards boards = (Boards) entityMap.get("boards");
    List<Photos> photosList =
        (List<Photos>) entityMap.get("photosList");
    boardsRepository.save(boards);
    if (photosList != null) {
      photosList.forEach(new Consumer<Photos>() {
        @Override
        public void accept(Photos photos) {
          photosRepository.save(photos);
        }
      });
    }
    return boards.getBno();
  }

  @Override
  public PageResultDTO<BoardsDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());
    // Page<Feeds> result = feedsRepository.findAll(pageable);
//    Page<Object[]> result = feedsRepository.getListPageImg(pageable);
    Page<Object[]> result = boardsRepository.searchPage(pageRequestDTO.getType(),
        pageRequestDTO.getKeyword(),
        pageable);
    Function<Object[], BoardsDTO> fn = objects -> entityToDto(
        (Boards) objects[0],
        (List<Photos>) (Arrays.asList((Photos) objects[1])),
        (Long) objects[2],
        (Long) objects[3]
    );
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public BoardsDTO getBoards(Long bno) {
    List<Object[]> result = boardsRepository.getBoardsWithAll(bno);
    Boards boards = (Boards) result.get(0)[0];
    List<Photos> photos = new ArrayList<>();
    result.forEach(objects -> photos.add((Photos) objects[1]));
    Long likes = (Long) result.get(0)[2];
    Long reviewsCnt = (Long) result.get(0)[3];

    return entityToDto(boards, photos, likes, reviewsCnt);
  }

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @Transactional
  @Override
  public void modify(BoardsDTO boardsDTO) {
    Optional<Boards> result = boardsRepository.findById(boardsDTO.getBno());
    if (result.isPresent()) {
      Map<String, Object> entityMap = dtoToEntity(boardsDTO);
      Boards boards = (Boards) entityMap.get("boards");
      boards.changeTitle(boardsDTO.getTitle());
      boardsRepository.save(boards);
      // photosList :: 수정창에서 이미지 수정할 게 있는 경우의 목록
      List<Photos> newPhotosList =
          (List<Photos>) entityMap.get("photosList");

      List<Photos> oldPhotosList =
          photosRepository.findByMid(boards.getBno());
      if (newPhotosList == null) {
        // 수정창에서 이미지 모두를 지웠을 때
        photosRepository.deleteByBno(boards.getBno());
        for (int i = 0; i < oldPhotosList.size(); i++) {
          Photos oldPhotos = oldPhotosList.get(i);
          String fileName = oldPhotos.getPath() + File.separator
              + oldPhotos.getUuid() + "_" + oldPhotos.getPhotosName();
          deleteFile(fileName);
        }
      } else { // newFeedsImageList에 일부 변화 발생
        newPhotosList.forEach(photos -> {
          boolean result1 = false;
          for (int i = 0; i < oldPhotosList.size(); i++) {
            result1 = oldPhotosList.get(i).getUuid().equals(photos.getUuid());
            if (result1) break;
          }
          if (!result1) photosRepository.save(photos);
        });
        oldPhotosList.forEach(oldPhotos -> {
          boolean result1 = false;
          for (int i = 0; i < newPhotosList.size(); i++) {
            result1 = newPhotosList.get(i).getUuid().equals(oldPhotos.getUuid());
            if (result1) break;
          }
          if (!result1) {
            photosRepository.deleteByUuid(oldPhotos.getUuid());
            String fileName = oldPhotos.getPath() + File.separator
                + oldPhotos.getUuid() + "_" + oldPhotos.getPhotosName();
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
  public List<String> removeWithReviewsAndPhotos(Long bno) {
    List<Photos> list = photosRepository.findByMid(bno);
    List<String> result = new ArrayList<>();
    list.forEach(new Consumer<Photos>() {
      @Override
      public void accept(Photos t) {
        result.add(t.getPath() + File.separator + t.getUuid() + "_" + t.getPhotosName());
      }
    });
    photosRepository.deleteByBno(bno);
    reviewsRepository.deleteByBno(bno);
    boardsRepository.deleteById(bno);
    return result;
  }

  @Override
  public void removeUuid(String uuid) {
    log.info("deleteImage...... uuid: " + uuid);
    photosRepository.deleteByUuid(uuid);
  }
}
