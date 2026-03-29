package com.limou.so.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.limou.so.model.entity.Post;
import com.limou.so.model.entity.PostThumb;
import com.limou.so.model.entity.User;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 帖子点赞服务测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class PostThumbServiceTest {

    @Resource
    private PostThumbService postThumbService;

    private static final User loginUser = new User();

    @BeforeAll
    static void setUp() {
        loginUser.setId(1L);
    }

    @Test
    void doPostThumb() {
        int i = postThumbService.doPostThumb(1L, loginUser);
        Assertions.assertTrue(i >= 0);
    }

    @Test
    void addPostThumb() {
        for (long i = 1000L; i < 20000; i++) {
            PostThumb postThumb1 = new PostThumb();
            postThumb1.setPostId(i);
            postThumb1.setUserId(i+1);
            postThumbService.save(postThumb1);
        }
    }

    @Test
    void testQueryAll(){
//        List<PostThumb> list = postThumbService.list();
        QueryWrapper<PostThumb> queryWrapper = new QueryWrapper<>();
        QueryWrapper<PostThumb> select = queryWrapper.select("postId", "nickName");
        List<PostThumb> list1 = postThumbService.list(select);

    }


}
