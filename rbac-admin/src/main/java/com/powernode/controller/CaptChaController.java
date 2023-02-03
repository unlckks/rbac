package com.powernode.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.enums.ResultEnums;
import com.powernode.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用hutool生成验证码
 * 验证码放入session
 * 验证码图片转化成字符串
 * 字符串响应出去
 *
 * @Author: MingYun
 * @Date: 2023-02-03 15:20
 */
@RestController
public class CaptChaController  extends BaseController{
    /**
     * 得到验证码
     */
    @GetMapping(CoreConstant.CAPTCHA_URL)
    public Result getCaptcha(){
        //生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(160, 60, 1, 30);
        //把正确的验证码对象放到session
        ServletUtils.getSession().setAttribute(CoreConstant.CAPTCHA_KEY,captcha);
        //把验证码转成String
        String base64Data = captcha.getImageBase64Data();
        System.out.println("captcha = " + captcha.getCode());
        return new Result(ResultEnums.SUCCESS.getCode(),ResultEnums.SUCCESS.getMsg(),base64Data);
    }

}
