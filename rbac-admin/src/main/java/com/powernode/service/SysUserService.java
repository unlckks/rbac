package com.powernode.service;

import com.powernode.domain.SysUser;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-03 15:09
 */
public interface SysUserService{


    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

        /**
         * 用户用于登录的service
         * @param username
         * @param password
         * @return
         */
        SysUser login(String username, String password );

    /**
     * 超级用户用户查询所有的员工
     * @return
     */
    List<String> queryAllPermissions();

    /**
     * 普通用户根据id查询
     * @param userId
     * @return
     */
    List<String> queryPermissionsByUserId(Long userId);
}
