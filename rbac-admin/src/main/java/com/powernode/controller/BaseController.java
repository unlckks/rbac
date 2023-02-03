package com.powernode.controller;

import com.powernode.domain.SysUser;
import com.powernode.utils.SecurityUtils;

/**
 * @Author: MingYun
 * @Date: 2023-02-03 15:18
 */
public class BaseController {
    /**
     * 得到当前用户的数据
     */
    public SysUser getCurrentUser(){
         return SecurityUtils.getCurrentUser();
    }
    /**
     * 得到用户名
     *
     */
    public  String getUsername(){
        return  getCurrentUser().getUserName();
    }
}
