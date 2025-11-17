package com.limou.so.dataSource;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.dto.user.UserQueryRequest;
import com.limou.so.model.entity.User;
import com.limou.so.model.vo.UserVO;
import com.limou.so.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserDataSource implements DataSource<UserVO> {
    @Resource
    private UserService userService;
    @Override
    public Page<UserVO> doSearch(String searchText, int current, int pageSize) {
       SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchText(searchText);
        searchRequest.setCurrent(current);
        searchRequest.setPageSize(pageSize);
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        BeanUtil.copyProperties(searchRequest, userQueryRequest);
        return userService.listUserVOByPage(userQueryRequest);
    }
}
