package com.limou.so.service;


import com.limou.so.model.dto.picture.PictureRequest;
import com.limou.so.model.entity.Picture;

import java.util.List;

public interface PictureService {
    List<Picture> searchPicture(PictureRequest pictureRequest);
}
