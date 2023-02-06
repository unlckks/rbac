package com.powernode.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.crypto.digest.DigestUtil;

import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysUserService;
import com.powernode.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-03 15:28
 */
@RestController
public class LoginController  extends  BaseController{
    @Resource
    private SysUserService sysUserService ;

    /**
     * 登录方法
     *
     */
      @PostMapping("login")
    public Result<?> login (String username ,String password ,String code){
          //从session中取对象
          CircleCaptcha captcha= (CircleCaptcha) ServletUtils.getSession().getAttribute(CoreConstant.CAPTCHA_KEY);
         //判断
          if(captcha == null){
              return  new Result<>(ResultEnums.ERROR,"验证码未生成");
          }
          if(! captcha.verify(code)){
              return  new Result<>(ResultEnums.ERROR,"验证码不正确");
          }
        SysUser sysUser = sysUserService.login(username, DigestUtil.md5Hex(password));
          if(sysUser == null){
              return  new Result<>(ResultEnums.ERROR,"用户或密码不正确");
          }
          ServletUtils.getSession().setAttribute(CoreConstant.SESSION_KEY,sysUser);
          return new Result<>(ResultEnums.SUCCESS.getCode(),"登录成功","sysUser");

      }



    /**
     * 获取用户信息
     *
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(){
        //从session中取处对象
        SysUser user= (SysUser) ServletUtils.getSession().getAttribute(CoreConstant.SESSION_KEY);
        //创建一个map 用于存放session
        HashMap<Object, Object> data = new HashMap<>();
        user.setPassword("");
        data.put("user",user);
        //创建一个列表
        List<String> permissions =  new ArrayList<>();
        //判断用户类型
        if (user.getUserType().equals(CoreConstant.USER_TYPE_ADMIN)){
            //超级管理员,查询所有的Permission
           permissions = this.sysUserService.queryAllPermissions();
        }
        else {
            //普通用户
            permissions = this.sysUserService.queryPermissionsByUserId(user.getUserId());
        }
        data.put("permissions",permissions);
        return new Result(ResultEnums.SUCCESS,data);
    }

    /**
     * 用户退出
     *
     */

    @GetMapping("logout")
    public Result logout(){
        //让session无效，进行退出
        ServletUtils.getSession().invalidate();
        return new Result() ;
    }
}
