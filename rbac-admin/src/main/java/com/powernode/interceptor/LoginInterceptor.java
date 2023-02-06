package com.powernode.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.enums.ResultEnums;
import com.powernode.utils.ServletUtils;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 用于用户登录拦截器的使用
 * @Author: MingYun
 * @Date: 2023-02-03 16:51
 */
public class LoginInterceptor  implements HandlerInterceptor {


    //声明一个JACKSON的转化对象
    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //处理编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=utf-8");
        //从session里面得到用户对象
        Object obj = ServletUtils.getSession().getAttribute(CoreConstant.SESSION_KEY);
        if(obj == null ){
            response.getWriter().write(objectMapper.writeValueAsString(new Result<>(ResultEnums.USER_NOT_LOGIN)));
            return false;
        }
        return true;
    }
}
