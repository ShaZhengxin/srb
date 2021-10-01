package com.szx.srb.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.szx.common.exception.Assert;
import com.szx.common.exception.BusinessException;
import com.szx.common.result.ResponseEnum;
import com.szx.srb.sms.service.SmsService;
import com.szx.srb.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author szx
 * @description TODO
 * @date 2021/10/1  16:53
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService
{
    @Override
    public void send(String mobile, String templateCode, Map<String, Object> param)  {
        //创建远程连接客户端
        DefaultProfile profile=DefaultProfile.getProfile(
                SmsProperties.REGION_ID,
                SmsProperties.KEY_ID,
                SmsProperties.KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        //创建远程连接的请求参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        //远程服务器地址
        request.setSysDomain("dysmsapi.aliyuncs.com");
        //API版本号
        request.setSysVersion("2017-05-25");
        //当前远程请求要做的事：发送短信
        request.setSysAction("SendSms");
        //参数
        request.putQueryParameter("RegionId",SmsProperties.REGION_ID);
        request.putQueryParameter("PhoneNumbers",mobile);
        request.putQueryParameter("SignName",SmsProperties.SIGN_NAME);
        request.putQueryParameter("TemplateCode",templateCode);

        Gson gson=new Gson();
        //json字符串
        String json = gson.toJson(param);
        request.putQueryParameter("TemplateParam",json);

        try {
            //使用客户端对象携带请求对象发送请求并得到响应结果
            CommonResponse response = client.getCommonResponse(request);
            /*
                response body:{
                        "RequestId":"xxxxxxxxxxxxxx",
                        "Message":"OK",
                        "Bizld":"yyyyyyyyyy",
                        "Code":"OK"
                }
             */
            //通信失败
            boolean success = response.getHttpResponse().isSuccess();
            Assert.isTrue(success,ResponseEnum.ALIYUN_SMS_RESPONSE_ERROR);


            //获取响应结果
            String data = response.getData();
            //json字符串转换为map集合
            HashMap<String,String> resultMap = gson.fromJson(data, HashMap.class);
            String code = resultMap.get("Code");
            String message = resultMap.get("Message");
            log.info("阿里云短信发送响应结果：");
            log.info("code：" + code);
            log.info("message：" + message);


            //业务失败
            //ALIYUN_SMS_LIMIT_CONTROL_ERROR(-502, "短信发送过于频繁"),//业务限流
            Assert.notEquals("isv.BUSINESS_LIMIT_CONTROL",code,ResponseEnum.ALIYUN_SMS_LIMIT_CONTROL_ERROR);
            //ALIYUN_SMS_ERROR(-503, "短信发送失败"),//其他失败
            Assert.equals("OK",code,ResponseEnum.ALIYUN_SMS_ERROR);

        } catch (ServerException e) {
            log.error("阿里云短信发送SDK调用失败：");
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
            //给前端返回的错误
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR , e);
        } catch (ClientException e) {
            log.error("阿里云短信发送SDK调用失败：");
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR , e);
        }
    }
}
