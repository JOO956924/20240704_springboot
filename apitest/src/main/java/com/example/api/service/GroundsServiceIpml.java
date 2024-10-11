package com.example.api.service;

import com.example.api.dto.GroundsDTO;
import com.example.api.dto.PageRequestDTO;
import com.example.api.dto.PageResultDTO;
import com.example.api.entity.Grounds;
import com.example.api.entity.GroundsPhotos;
import com.example.api.repository.*;
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
public class GroundsServiceIpml implements GroundsService{
  private final GroundsRepository groundsRepository;
  private final GroundsPhotosRepository groundsPhotosRepository;
  private final GroundsReviwsRepository groundsReviwsRepository;

  @Override
  public Long register(GroundsDTO groundsDTO) {
    Map<String, Object> entityMap = dtoToEntity(groundsDTO);
    Grounds grounds = (Grounds) entityMap.get("grounds");
    List<GroundsPhotos> photosList =
        (List<GroundsPhotos>) entityMap.get("photosList");
    groundsRepository.save(grounds);
    if (photosList != null) {
      photosList.forEach(new Consumer<GroundsPhotos>() {
        @Override
        public void accept(GroundsPhotos groundsphotos) {
          groundsPhotosRepository.save(groundsphotos);
        }
      });
    }
    return grounds.getGno();
  }

  @Override
  public PageResultDTO<GroundsDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());
    // Page<Feeds> result = feedsRepository.findAll(pageable);
//    Page<Object[]> result = feedsRepository.getListPageImg(pageable);
    Page<Object[]> result = groundsRepository.searchPage(pageRequestDTO.getType(),
        pageRequestDTO.getKeyword(),
        pageable);
    Function<Object[], GroundsDTO> fn = objects -> entityToDto(
        (Grounds) objects[0],
        (List<GroundsPhotos>) (Arrays.asList((GroundsPhotos) objects[1])),
        (String) objects[2],
        (String) objects[3]
    );
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public GroundsDTO getGrounds(Long gno) {
    List<Object[]> result = groundsRepository.findByGnoAndNotFinished(gno);
    Grounds grounds = (Grounds) result.get(0)[0];
    List<GroundsPhotos> groundsphotos = new ArrayList<>();
    result.forEach(objects -> groundsphotos.add((GroundsPhotos) objects[1]));
    String location = (String) result.get(0)[2];
    String sports = (String) result.get(0)[3];

    return entityToDto(grounds, groundsphotos, location, sports);
  }

  @Value("${com.example.upload.path}")
  private String uploadPath;

  @Transactional
  @Override
  public void modify(GroundsDTO groundsDTO) {
    Optional<Grounds> result = groundsRepository.findById(groundsDTO.getGno());
    if (result.isPresent()) {
      Map<String, Object> entityMap = dtoToEntity(groundsDTO);
      Grounds grounds = (Grounds) entityMap.get("grounds");
      grounds.changegTitle(groundsDTO.getGtitle());
      groundsRepository.save(grounds);
      // photosList :: 수정창에서 이미지 수정할 게 있는 경우의 목록
      List<GroundsPhotos> newPhotosList =
          (List<GroundsPhotos>) entityMap.get("photosList");

      List<GroundsPhotos> oldPhotosList =
          groundsPhotosRepository.findByMid(grounds.getGno());
      if (newPhotosList == null) {
        // 수정창에서 이미지 모두를 지웠을 때
        groundsPhotosRepository.deleteByGno(grounds.getGno());
        for (int i = 0; i < oldPhotosList.size(); i++) {
          GroundsPhotos oldPhotos = oldPhotosList.get(i);
          String fileName = oldPhotos.getPath() + File.separator
              + oldPhotos.getUuid() + "_" + oldPhotos.getPhotosName();
          deleteFile(fileName);
        }
      } else { // newFeedsImageList에 일부 변화 발생
        newPhotosList.forEach(groundsphotos -> {
          boolean result1 = false;
          for (int i = 0; i < oldPhotosList.size(); i++) {
            result1 = oldPhotosList.get(i).getUuid().equals(groundsphotos.getUuid());
            if (result1) break;
          }
          if (!result1) groundsPhotosRepository.save(groundsphotos);
        });
        oldPhotosList.forEach(oldPhotos -> {
          boolean result1 = false;
          for (int i = 0; i < newPhotosList.size(); i++) {
            result1 = newPhotosList.get(i).getUuid().equals(oldPhotos.getUuid());
            if (result1) break;
          }
          if (!result1) {
            groundsPhotosRepository.deleteByUuid(oldPhotos.getUuid());
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
  public List<String> removeWithReviewsAndPhotos(Long gno) {
    List<GroundsPhotos> list = groundsPhotosRepository.findByMid(gno);
    List<String> result = new ArrayList<>();
    list.forEach(new Consumer<GroundsPhotos>() {
      @Override
      public void accept(GroundsPhotos t) {
        result.add(t.getPath() + File.separator + t.getUuid() + "_" + t.getPhotosName());
      }
    });
    groundsPhotosRepository.deleteByGno(gno);
    groundsReviwsRepository.deleteByGno(gno);
    groundsRepository.deleteById(gno);
    return result;
  }

  @Override
  public void removeUuid(String uuid) {
    log.info("deleteImage...... uuid: " + uuid);
    groundsPhotosRepository.deleteByUuid(uuid);
  }
}
