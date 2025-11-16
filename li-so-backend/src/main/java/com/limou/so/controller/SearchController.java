package com.limou.so.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.common.BaseResponse;
import com.limou.so.common.ErrorCode;
import com.limou.so.common.ResultUtils;
import com.limou.so.model.dto.post.PostQueryRequest;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.dto.user.UserQueryRequest;
import com.limou.so.model.entity.Picture;
import com.limou.so.model.entity.User;
import com.limou.so.model.vo.PostVO;
import com.limou.so.model.vo.SearchVO;
import com.limou.so.model.vo.UserVO;
import com.limou.so.service.PictureService;
import com.limou.so.service.PostService;
import com.limou.so.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;


    //对请求进行统一的封装
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {
        //判断
        if (searchRequest == null) {
            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
        }
        String searchText = searchRequest.getSearchText();
        //调用不同的接口进行统一的封装 图片接口
        CompletableFuture<Page<Picture>> pagePictureCompletableFuture = CompletableFuture.supplyAsync(() -> {
            //图片接口
            return pictureService.searchPicture(searchRequest);
        });
//        Page<Picture> picturePage = pictureService.searchPicture(searchRequest);

        //用户接口
        CompletableFuture<Page<UserVO>> pageUserCompletableFuture = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            BeanUtil.copyProperties(searchRequest, userQueryRequest);
            return userService.listUserVOByPage(userQueryRequest);
        });
        //文章接口
//        PostQueryRequest postQueryRequest = new PostQueryRequest();
//        postQueryRequest.setSearchText(searchText);
//        BeanUtil.copyProperties(searchRequest, postQueryRequest);
//        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest);
//
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
        SearchVO searchVO = new SearchVO();
        searchVO.setUserVOList(userVOPage.getRecords());
        searchVO.setPostVOList(postVOPage.getRecords());
        searchVO.setPictureList(picturePage.getRecords());

        return ResultUtils.success(searchVO);
    }

}
