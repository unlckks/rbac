package com.powernode.async;

import com.powernode.domain.SysLoginInfo;
import com.powernode.service.SysLoginInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: MingYun
 * @Date: 2023-02-10 14:18
 */
@Component
@Async
@EnableAsync
public class AsyncSysLoginLogInfoService {
    @Resource
    private SysLoginInfoService loginInfoService ;

    /**
     * 登录日志
     */
    public  void saveLoginLogInfo(SysLoginInfo sysLoginInfo){
        String ipaddr =sysLoginInfo.getIpAddr();
        //处理IP
        if(ipaddr !=null){
            if (ipaddr.equalsIgnoreCase("127.0.0.1") || ipaddr.equalsIgnoreCase("localhost")){
                sysLoginInfo.setLoginLocation("本地");
            }else {
                 return  ;
            }
        }
        this.loginInfoService.insertSelective(sysLoginInfo);
    }

}
