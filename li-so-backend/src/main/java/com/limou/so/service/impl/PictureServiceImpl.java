package com.limou.so.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.limou.so.common.ErrorCode;
import com.limou.so.exception.BusinessException;
import com.limou.so.exception.ThrowUtils;
import com.limou.so.model.dto.picture.PictureRequest;
import com.limou.so.model.dto.search.SearchRequest;
import com.limou.so.model.entity.Picture;
import com.limou.so.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public Page<Picture> searchPicture(SearchRequest searchRequest) {
         //使用jsoup爬取页面的图片并且返回给前端
        //1.获取url
        String searchText = searchRequest.getSearchText();
        int pageSize = searchRequest.getPageSize();
        String fetchUrl = String.format("https://cn.bing.com/images/async?q=%s&mmasync=1", searchText);
        Document document = null;
        try {
            document = Jsoup.connect(fetchUrl).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数不存在");
        }
        //读取页面获取url和文章标题title
        ArrayList<Picture> pictures = new ArrayList<>();
        Elements elements = document.select(".iuscp.isv");
        for (Element element : elements) {
            //获取图片的url
            String m = element.select(".iusc").get(0).attr("m");
            //1.转换成map的到里面的数据
            Map map = JSONUtil.toBean(m, HashMap.class);
            Object murl = map.get("murl");
            Object title = map.get("t");
            //2.得到murl,和title
            Picture picture = new Picture();
            picture.setTitle(title.toString());
            picture.setUrl(murl.toString());
            pictures.add(picture);
            if(pictures.size()>=pageSize){
                break;
            }
        }

        Page<Picture> pagePicture = new Page<>(searchRequest.getCurrent(), searchRequest.getPageSize(), pictures.size());
        pagePicture.setRecords(pictures);
        return pagePicture;
    }
}
