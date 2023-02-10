package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysRole;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-08 11:06
 */
public interface SysRoleService{


    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 分页查询角色
     * @param page
     * @return
     */
        Page<SysRole> getByPage(Page<SysRole> page);

    /**
     * 根据角色ID查询当前角色拥有的目录权限及菜单ID
     * @param roleId
     * @return
     */
        List<Long> queryCurrentRoleHasMenuIdsByRoleId(Long roleId);

    /**
     *保存菜单和角色之间的关系
     * @param roleId
     * @param menuIds
     */
    void saveRoleMenu(Long roleId, Long[] menuIds);

    /**
     * 查询所有角色不分页
     * @return
     */
    List<SysRole> queryAllRoles();

    /**
     * 根据用户ID查询用户已拥有的角色IDS
     * @param userId
     * @return
     */
    List<Long> queryRoleIdsByUserId(Long userId);

    /**
     * 保存用户和角色之间的关系
     * @param userId
     * @param roleIds
     */
    void saveUserRoles(Long userId, Long[] roleIds);
}
