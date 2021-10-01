package com.szx.srb.sms;

import com.szx.srb.sms.util.SmsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author szx
 * @description TODO
 * @date 2021/9/2  0:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)//有了@RunWith(SpringRunner.class)这些类才能实例化到spring容器中，自动注入才能生效，不然直接一个NullPointerExecption
public class UtilsTest {

    @Test
    public void testProperties(){
        System.out.println(SmsProperties.KEY_ID);
        System.out.println(SmsProperties.KEY_SECRET);
        System.out.println(SmsProperties.REGION_ID);
        System.out.println(SmsProperties.SIGN_NAME);
        System.out.println(SmsProperties.TEMPLATE_CODE);
    }
}
