package com.szx.srb.sms.controller.api;

import com.szx.common.exception.Assert;
import com.szx.common.result.R;
import com.szx.common.result.ResponseEnum;
import com.szx.common.util.RandomUtils;
import com.szx.common.util.RegexValidateUtils;
import com.szx.srb.sms.service.SmsService;
import com.szx.srb.sms.util.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author szx
 * @description TODO
 * @date 2021/10/1  17:53
 */
@Controller
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@CrossOrigin
@Slf4j
public class ApiSmsController {

    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable("mobile") String mobile) {

        //校验手机号不为空，MOBILE_NULL_ERROR(-202, "手机号不能为空")
        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);

        //校验手机号合法性, MOBILE_ERROR(-203, "手机号不正确")
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        Map<String, Object> map = new HashMap<>();
        //生成验证码
        String code = RandomUtils.getFourBitRandom();
        // 组装短信模板参数
        map.put("code", code);
        //发送短信
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, map);

        //将验证码存入redis,设置过期时间
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile,code, 5, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");
    }
}
