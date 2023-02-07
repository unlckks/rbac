package com.powernode.mapper;


import com.powernode.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-07 10:00
 */
public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    /**
     * 查询所有菜单权限
     *
     * @param sysMenu
     * @return
     */

    List<SysMenu> queryAllMenuList(@Param("menu") SysMenu sysMenu);

    /**
     * 普通用户 ,更具用户ID查询拥有的菜单和权限
     *
     * @param
     * @param userId
     * @return
     */
    List<SysMenu> selectMenuListByUserId(@Param("menu") SysMenu Menu, @Param("userId") Long userId
    );

    Integer queryChildrenCountByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据菜单类型与节点查询最大得id
     *
     * @param menuType
     * @param parentId
     * @return
     */
    Long queryMaxMenuIdByMenuType(@Param("menuType") Integer menuType, @Param("parentId") Long parentId
    );

    /**
     * 查询所有的目录与菜单
     *
     * @return
     */
    List<SysMenu> selectMenuListMC();

    /**
     * 根据用于id查询当前用户
     *
     * @param userId
     * @return
     */
    List<SysMenu> selectMenuMCByUserId(@Param("userId") Long userId);
}