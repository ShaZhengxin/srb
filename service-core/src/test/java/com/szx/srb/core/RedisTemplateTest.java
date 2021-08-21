package com.szx.srb.core;

import com.szx.srb.core.mapper.DictMapper;
import com.szx.srb.core.pojo.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author szx
 * @description TODO
 * @date 2021/8/19  23:42
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DictMapper dictMapper;

    @Test
    public void saveDict(){
        Dict dict = dictMapper.selectById(1);
        //jdk原生序列化，安全性低
        redisTemplate.opsForValue().set("dict",dict,5, TimeUnit.MINUTES);//向数据库中存储string类型的键值对, 过期时间5分钟

    }

    @Test
    public void getDict(){
        Dict dict = (Dict)redisTemplate.opsForValue().get("dict");
        System.out.println(dict);
    }
}
