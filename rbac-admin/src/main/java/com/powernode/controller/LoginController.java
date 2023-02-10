package com.powernode.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.crypto.digest.DigestUtil;

import com.powernode.async.AsyncSysLoginLogInfoService;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysLoginInfo;
import com.powernode.domain.SysUser;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysUserService;
import com.powernode.utils.HttpUtils;
import com.powernode.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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



    @Resource
    private AsyncSysLoginLogInfoService loginLogInfoService;
    /**
     * 登录方法
     *
     */
      @PostMapping("login")
    public Result<?> login (String username ,String password ,String code) {
          //构造要保存的对象
          SysLoginInfo sysLoginInfo = new SysLoginInfo();
          //设置日志登录时间
          sysLoginInfo.setLoginTime(new Date());
          //设置浏览器地址
          sysLoginInfo.setBrowser(HttpUtils.getBrowserName(ServletUtils.getRequest()));
          //设置ip地址
          sysLoginInfo.setIpAddr(HttpUtils.getIpAddr(ServletUtils.getRequest()));
          //设置操作系统名称
          sysLoginInfo.setOs(HttpUtils.getOsName(ServletUtils.getRequest()));
          //登录人
          sysLoginInfo.setUserName(username);
          //从session中取对象
          CircleCaptcha captcha = (CircleCaptcha) ServletUtils.getSession().getAttribute(CoreConstant.CAPTCHA_KEY);

          //判断
          try {
              if (captcha == null) {
                  sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());
                  sysLoginInfo.setMsg("验证码未生成");
                  return new Result<>(ResultEnums.ERROR, "验证码未生成");
              }
          if (!captcha.verify(code)) {
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());
                   sysLoginInfo.setMsg("验证码不正确");
              return new Result<>(ResultEnums.ERROR, "验证码不正确");
          }
          SysUser sysUser = sysUserService.login(username, DigestUtil.md5Hex(password));
          if (sysUser == null) {
              return new Result<>(ResultEnums.ERROR, "用户或密码不正确");
          }
          ServletUtils.getSession().setAttribute(CoreConstant.SESSION_KEY, sysUser);
          sysLoginInfo.setStatus(StateEnums.LOGIN_SUCCESS.getCode());
          sysLoginInfo.setMsg("登陆成功");
          return new Result<>(ResultEnums.SUCCESS.getCode(), "登录成功", "sysUser");
          } finally {
              loginLogInfoService.saveLoginLogInfo(sysLoginInfo);
          }

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
