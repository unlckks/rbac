package com.powernode.service;

import com.powernode.domain.SysDept;
import com.powernode.domain.SysMenu;
import com.powernode.domain.SysUser;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-07 10:00
 */
public interface SysMenuService{


    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    /**
     * 查询所有菜单权限
     * @param sysMenu
     * @param currentUser
     * @return
     */
        List<SysMenu> queryAllMenuList(SysMenu sysMenu, SysUser currentUser);


    /**
     * 根据id查子节点
     * @param menuId
     * @return
     */
    Integer queryChildrenCountByMenuId(Long menuId);

    /**
     *
     * @param currentUser
     * @return
     */
    List<SysMenu> queryAllMenuListMC(SysUser currentUser);
}
