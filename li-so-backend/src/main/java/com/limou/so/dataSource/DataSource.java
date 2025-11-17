package com.limou.so.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

public interface DataSource<T> {
    Page<T> doSearch(String searchText, int current, int pageSize);
}
