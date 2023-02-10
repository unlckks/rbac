package com.powernode.mapper;

import com.powernode.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: MingYun
 * @Date: 2023-02-08 11:06
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 根据条件查询角色
     *
     * @param params
     * @return
     */
    List<SysRole> queryAllRole(@Param("params") Map<String, Object> params);

    /**
     * 根据角色ID删除角色和菜单的关系
     *
     * @param roleId
     */
    void deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID删除角色和用户的关系
     *
     * @param roleId
     */
    void deleteUserRoleByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID查询当前角色拥有的权限菜单
     *
     * @param roleId
     * @return
     */
    List<Long> queryCurrentRoleHasMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 保存角色 和菜单的关系
     *
     * @param roleId
     * @param menuId
     */
    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 保存角色 和菜单的关系
     *
     * @param roleId
     * @param menuIds
     */
    void saveRoleMenuBatch(@Param("roleId") Long roleId, @Param("menuIds") Long[] menuIds);

    /**
     * 删除当前用户ID对应的角色关系
     *
     * @param userId
     */
    void deleteUserRoleByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询用户已拥有的角色IDS
     *
     * @param userId
     * @return
     */
    List<Long> queryRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 保存角色和用户的有关系数据
     *
     * @param userId
     * @param roleIds
     */
    void saveUserRoles(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);

}