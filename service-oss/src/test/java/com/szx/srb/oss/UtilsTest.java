package com.szx.srb.oss;

import com.szx.srb.oss.util.OssProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author szx
 * @description TODO
 * @date 2021/10/1  22:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)//有了@RunWith(SpringRunner.class)这些类才能实例化到spring容器中，自动注入才能生效，不然直接一个NullPointerExecption
public class UtilsTest {

    @Test
    public void testProperties(){
        System.out.println(OssProperties.KEY_ID);
        System.out.println(OssProperties.KEY_SECRET);
        System.out.println(OssProperties.BUCKET_NAME);
        System.out.println(OssProperties.ENDPOINT);
    }
}