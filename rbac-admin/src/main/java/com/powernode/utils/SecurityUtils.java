package com.powernode.utils;

import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;

/**
 * @Author: MingYun
 * @Date: 2023-02-03 15:10
 */
public class SecurityUtils {


    /**
     * 得到当前用户的数据
     */
    public  static SysUser getCurrentUser(){
        return (SysUser) ServletUtils.getSession().getAttribute(CoreConstant.SESSION_KEY) ;
    }
    /**
     * 得到用户名
     *
     */
    public static String getUsername(){
        return getCurrentUser().getUserName();
    }
}
