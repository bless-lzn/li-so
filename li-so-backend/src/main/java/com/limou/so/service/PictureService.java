package com.limou.so.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.model.dto.picture.PictureRequest;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.entity.Picture;

import java.util.List;

public interface PictureService {
    Page<Picture> searchPicture(SearchRequest searchRequest);
}
