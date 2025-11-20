package com.limou.so.esdao;

import com.limou.so.model.dto.post.PostEsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostEsDaoTest {
    @Resource
    private PostEsDao postEsDao;

    @Test
    void findByUserId() {
        List<PostEsDTO> byUserId = postEsDao.findByUserId(1L);
        System.out.println(byUserId);
    }
    @Test
    void add(){
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
        postEsDTO.setTitle("测试");
        postEsDTO.setContent("测试");
        postEsDTO.setUserId(1L);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO);
    }
}