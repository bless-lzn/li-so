package com.limou.so.utils;

import cn.hutool.json.JSONUtil;
import com.limou.so.model.entity.Picture;
import com.limou.so.model.entity.Post;
import com.limou.so.service.PostService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class spiderTest {
    @Resource
    private PostService postService;

//    @Test
    public void test() {
        //使用jsoup或者http无头请求
        String Url = "https://www.codefather.cn/post/passage";
        try {
            Document document = Jsoup.connect(Url).get();
            // 选择 class 为 line-clamp-1 font-medium text-gray-900 的 div 元素
            Elements targetElements = document.select("div.line-clamp-1.font-medium.text-gray-900");
            //解析里面的数据将数据放到自己的数据库 中
            ArrayList<Post> list = new ArrayList<>();
            for (Element element : targetElements) {
                Post post = new Post();
                post.setTitle("好东西文章标题");
                post.setContent(element.text());
                post.setTags("test");
                post.setThumbNum(10);
                post.setFavourNum(10);
                post.setUserId(123L);
                list.add(post);
            }
            postService.saveBatch(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    爬取页面中的picture图片
    @Test
    void testFetchPicture() throws IOException {
        //使用jsoup爬取页面的图片并且返回给前端
        //1.获取url
        String searchText = "美女";
        String fetchUrl = String.format("https://cn.bing.com/images/async?q=%s&mmasync=1", searchText);
        Document document = Jsoup.connect(fetchUrl).get();
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
            System.out.println( picture);
        }


    }


}
