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

    List<SysRole> queryAllRole(@Param("params") Map<String, Object> params);

    /**
     * 根据角色ID查询当前角色拥有的目录权限及菜单ID
     *
     * @param roleId
     * @return
     */
    List<Long> queryCurrentRoleHasMenuIdsByRoleId(@Param("roleId") Long roleId
    );

    /**
     * 根据角色ID删除角色和菜单关系表的数据
     *
     * @param roleId
     */
    void deleteRoleUserByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID删除角色和用户关系表的数据
     * @param roleId
     */
    void deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 进行保存
     * @param roleId
     * @param menuId
     */
    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
}