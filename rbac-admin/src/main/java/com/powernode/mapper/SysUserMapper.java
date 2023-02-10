package com.powernode.mapper;

import com.powernode.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-09 15:59
 */
public interface SysUserMapper {
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
    SysUser login(@Param("username") String username, @Param("password") String password);

    /**
     * 查询所有权限
     *
     * @return
     */
    List<String> queryAllPermissions();

    /**
     * 根据用户ID查询权限
     *
     * @param userId
     * @return
     */
    List<String> queryPermissionsByUserId(@Param("userId") Long userId);

    /**
     * 根据条件查询用户数据
     *
     * @param params
     * @return
     */
    List<SysUser> queryAllUser(@Param("params") Map<String, Object> params);

    /**
     * 根据用户名查询用户对象
     *
     * @param userName
     * @return
     */
    SysUser queryUserByUserName(@Param("userName") String userName);
}