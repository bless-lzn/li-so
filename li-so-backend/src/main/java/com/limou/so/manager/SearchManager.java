package com.limou.so.manager;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.common.BaseResponse;
import com.limou.so.common.ResultUtils;
import com.limou.so.model.dto.post.PostQueryRequest;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.dto.user.UserQueryRequest;
import com.limou.so.model.entity.Picture;
import com.limou.so.model.enums.SearchTypeEnum;
import com.limou.so.model.vo.PostVO;
import com.limou.so.model.vo.SearchVO;
import com.limou.so.model.vo.UserVO;
import com.limou.so.service.PictureService;
import com.limou.so.service.PostService;
import com.limou.so.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@Component
public class SearchManager {
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;
    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {
        //判断
        String type = searchRequest.getType();
//        if (StrUtil.isBlank(type)) {
//            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
//        }
        //得到枚举
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        String searchText = searchRequest.getSearchText();
        //转变为 vo
        SearchVO searchVO = new SearchVO();
        if (searchTypeEnum == null) {
            //调用不同的接口进行统一的封装 图片接口
            CompletableFuture<Page<Picture>> pagePictureCompletableFuture = CompletableFuture.supplyAsync(() -> {
                //图片接口
                return pictureService.searchPicture(searchRequest);
            });
            CompletableFuture<Page<UserVO>> pageUserCompletableFuture = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                BeanUtil.copyProperties(searchRequest, userQueryRequest);
                return userService.listUserVOByPage(userQueryRequest);
            });
            CompletableFuture<Page<PostVO>> pagePostCompletableFuture = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                BeanUtil.copyProperties(searchRequest, postQueryRequest);
                return postService.listPostVOByPage(postQueryRequest);
            });
            //等待结果
            CompletableFuture.allOf(pageUserCompletableFuture, pagePostCompletableFuture, pagePictureCompletableFuture).join();
            Page<Picture> picturePage = pagePictureCompletableFuture.get();
            Page<UserVO> userVOPage = pageUserCompletableFuture.get();
            Page<PostVO> postVOPage = pagePostCompletableFuture.get();
            //转变为 vo
            searchVO.setUserVOList(userVOPage.getRecords());
            searchVO.setPostVOList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return searchVO;
        } else {
            switch (searchTypeEnum) {
                case PICTURE:
                    Page<Picture> picturePage = pictureService.searchPicture(searchRequest);
                    searchVO.setPictureList(picturePage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    BeanUtil.copyProperties(searchRequest, userQueryRequest);
                    Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                    searchVO.setUserVOList(userVOPage.getRecords());
                    break;
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    BeanUtil.copyProperties(searchRequest, postQueryRequest);
                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest);
                    searchVO.setPostVOList(postVOPage.getRecords());
                    break;
            }
        }
        return searchVO;
    }
}
