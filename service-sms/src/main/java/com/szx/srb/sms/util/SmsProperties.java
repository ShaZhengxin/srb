package com.szx.srb.sms.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author szx
 * @description TODO
 * @date 2021/9/1  0:40
 */
@Data // set方法进行初始化，get获取它
@Component //想使ConfigurationProperties生效，必须在spring容器中
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties implements InitializingBean {
  /*  region-id: cn-hangzhou #服务器在哪个区域
    key-id: 你的keyid      #权限的id和密码
    key-secret: 你的keysecret
    template-code: 你的短信模板code
    sign-name: 你的短信模板签名   #如：谷粒*/

    private String regionId;
    private String keyId;
    private String keySecret;
    private String templateCode;
    private String signName;


    public static String REGION_ID;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String TEMPLATE_CODE;
    public static String SIGN_NAME;

    //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_ID = regionId;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        TEMPLATE_CODE = templateCode;
        SIGN_NAME = signName;
    }
}
