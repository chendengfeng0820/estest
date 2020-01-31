package com.easyarch.estest;

import com.easyarch.estest.Bean.Atticle;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class EstestApplicationTests {

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() {
        //给es中索引（保存）一个文档
        Atticle atticle=new Atticle();
        atticle.setId(1);
        atticle.setTitle("好消息");
        atticle.setAuthor("cdf");
        atticle.setContent("hello world");

        Index index = new Index.Builder(atticle).index("com.easyarch.estest").type("news").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search(){
        //查询表达式
        String json="{content}";

        //构建搜索功能
        Search search = new Search.Builder(json).addIndex("com.easyarch.estest").addType("news").build();

        //执行
        SearchResult result = null;
        try {
            result = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}
