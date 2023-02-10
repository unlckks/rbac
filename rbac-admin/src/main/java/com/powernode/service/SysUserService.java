package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysUser;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-09 15:59
 */
public interface SysUserService{


    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    SysUser login(String username, String password);

    /**
     * 查询所有权限
     * @return
     */
    List<String> queryAllPermissions();

    /**
     * 根据用户ID查询用户拥有的权限
     * @param userId
     * @return
     */
    List<String> queryPermissionsByUserId(Long userId);

    /**
     * 分页查询用户
     * @param page
     * @return
     */
    Page<SysUser> getByPage(Page<SysUser> page);

    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    SysUser queryUserByUserName(String userName);

}
