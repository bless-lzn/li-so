package com.limou.so.dataSource;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.model.dto.post.PostQueryRequest;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.entity.Post;
import com.limou.so.model.vo.PostVO;
import com.limou.so.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class PostDataSource implements DataSource<PostVO> {
    @Resource
    private PostService postService;
    @Override
    public Page<PostVO> doSearch(String searchText, int current, int pageSize) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchText(searchText);
        searchRequest.setCurrent(current);
        searchRequest.setPageSize(pageSize);
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        BeanUtil.copyProperties(searchRequest, postQueryRequest);
        return postService.listPostVOByPage(postQueryRequest);
    }
}
