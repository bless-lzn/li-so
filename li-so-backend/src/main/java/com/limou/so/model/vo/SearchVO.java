package com.limou.so.model.vo;

import com.limou.so.common.PageRequest;
import com.limou.so.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchVO implements Serializable {

    List<UserVO> userVOList;

    List<PostVO> postVOList;

    List<Picture> pictureList;

    List<?> dataList;
    private static final long serialVersionUID = 1L;


}
