package com.limou.so.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.annotation.AuthCheck;
import com.limou.so.common.BaseResponse;
import com.limou.so.common.DeleteRequest;
import com.limou.so.common.ErrorCode;
import com.limou.so.common.ResultUtils;
import com.limou.so.constant.UserConstant;
import com.limou.so.exception.BusinessException;
import com.limou.so.exception.ThrowUtils;
import com.limou.so.model.dto.picture.PictureRequest;
import com.limou.so.model.dto.post.PostAddRequest;
import com.limou.so.model.dto.post.PostEditRequest;
import com.limou.so.model.dto.post.PostQueryRequest;
import com.limou.so.model.dto.post.PostUpdateRequest;
import com.limou.so.model.entity.Picture;
import com.limou.so.model.entity.Post;
import com.limou.so.model.entity.User;
import com.limou.so.model.vo.PostVO;
import com.limou.so.service.PictureService;
import com.limou.so.service.PostService;
import com.limou.so.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;


    /**
     * 分页获取列表（封装类）
     *
     * @param pictureRequest
     * @param request
     * @return
     */
    @PostMapping("/list/picture")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureRequest pictureRequest,
                                                        HttpServletRequest request) {
        long current = pictureRequest.getCurrent();
        long size = pictureRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        List<Picture> pictures = pictureService.searchPicture(pictureRequest);
        Page<Picture> pagePicture = new Page<>(pictureRequest.getCurrent(), pictureRequest.getPageSize(), pictures.size());
        pagePicture.setRecords(pictures);
        return ResultUtils.success(pagePicture);
    }
}
