package com.powernode.mapper;

import com.powernode.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-03 15:09
 */
public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 登录方法
     *
     * @param username
     * @param password
     * @return
     */
    SysUser login(@Param("username") String username, @Param("password") String password);

    /**
     * 查询所有用户权限
     * @return
     */
    List<String> queryAllPermissions();

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    List<String> queryPermissionsByUserId(@Param("userId") Long userId);
}