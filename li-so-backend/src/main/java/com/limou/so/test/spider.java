//package com.limou.so.test;
//
//import com.limou.so.mapper.PostMapper;
//import com.limou.so.model.entity.Post;
//import com.limou.so.service.PostService;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//@spr
//public class spider {
//    @Resource
//    private static PostService postService;
//    public static void main(String[] args) {
//        //使用jsoup或者http无头请求
//        String Url="https://www.codefather.cn/post/passage";
//        try {
//            Document document = Jsoup.connect(Url).get();
//            // 选择 class 为 line-clamp-1 font-medium text-gray-900 的 div 元素
//            Elements targetElements = document.select("div.line-clamp-1.font-medium.text-gray-900");
//            //解析里面的数据将数据放到自己的数据库 中
//            ArrayList<Post> list = new ArrayList<>();
//            for (Element element : targetElements) {
//                Post post = new Post();
//                post.setTitle(element.text());
//                post.setContent(element.text());
//                post.setTags("test");
//                post.setThumbNum(10);
//                post.setFavourNum(10);
//                post.setUserId(123L);
//                list.add(post);
//            }
//            postService.saveBatch(list);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
