package com.szx.srb.sms.service;

import java.util.Map;

/**
 * @author szx
 * @description TODO 短信发送业务
 * @date 2021/10/1  16:54
 */
public interface SmsService {
    void send(String mobile, String templateCode, Map<String,Object> param);
}
