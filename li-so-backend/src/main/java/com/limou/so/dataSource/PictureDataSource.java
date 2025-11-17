package com.limou.so.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.entity.Picture;
import com.limou.so.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class PictureDataSource implements DataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, int current, int pageSize) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchText(searchText);
        searchRequest.setCurrent(current);
        searchRequest.setPageSize(pageSize);
        return pictureService.searchPicture(searchRequest);
    }
}
