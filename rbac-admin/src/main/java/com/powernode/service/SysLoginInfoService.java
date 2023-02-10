package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysLoginInfo;
    /**
 *  @Author: MingYun
 *  @Date: 2023-02-10 14:15
 */
public interface SysLoginInfoService{


    int deleteByPrimaryKey(Long infoId);

    int insert(SysLoginInfo record);

    int insertSelective(SysLoginInfo record);

    SysLoginInfo selectByPrimaryKey(Long infoId);

    int updateByPrimaryKeySelective(SysLoginInfo record);

    int updateByPrimaryKey(SysLoginInfo record);

        /**
         * 登录日志分页
         * @param page
         * @return
         */
        Page<SysLoginInfo> getByPage(Page<SysLoginInfo> page);

    }
