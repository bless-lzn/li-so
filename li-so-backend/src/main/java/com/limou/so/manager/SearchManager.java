package com.limou.so.manager;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.common.BaseResponse;
import com.limou.so.common.ResultUtils;
import com.limou.so.dataSource.*;
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
import java.util.List;
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
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private DataSourceRegistry dataSourceRegistry;
    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws ExecutionException, InterruptedException {
        //判断
        String type = searchRequest.getType();
//        if (StrUtil.isBlank(type)) {
//            return new BaseResponse<>(ErrorCode.PARAMS_ERROR);
//        }
        //得到枚举
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        String searchText = searchRequest.getSearchText();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        //转变为 vo
        SearchVO searchVO = new SearchVO();
        if (searchTypeEnum == null) {
            //调用不同的接口进行统一的封装 图片接口
            CompletableFuture<Page<Picture>> pagePictureCompletableFuture = CompletableFuture.supplyAsync(() -> {
                //图片接口
                return pictureDataSource.doSearch(searchText, current, pageSize);
            });
            CompletableFuture<Page<UserVO>> pageUserCompletableFuture = CompletableFuture.supplyAsync(() -> {
                return userDataSource.doSearch(searchText, current, pageSize);
            });
            CompletableFuture<Page<PostVO>> pagePostCompletableFuture = CompletableFuture.supplyAsync(() -> {
                return postDataSource.doSearch(searchText, current, pageSize);
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
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> pageList = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList( pageList.getRecords());
//            switch (searchTypeEnum) {
//                case PICTURE:
//                    Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
//                    searchVO.setPictureList(picturePage.getRecords());
//                    break;
//                case USER:
//                    Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
//                    searchVO.setUserVOList(userVOPage.getRecords());
//                    break;
//                case POST:
//                    Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
//                    searchVO.setPostVOList(postVOPage.getRecords());
//                    break;
//            }
        }
        return searchVO;
    }
}
