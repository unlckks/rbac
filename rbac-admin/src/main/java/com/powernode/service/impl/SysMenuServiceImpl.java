package com.powernode.service.impl;

import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysDept;
import com.powernode.domain.SysUser;
import com.powernode.enums.StateEnums;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.powernode.domain.SysMenu;
import com.powernode.mapper.SysMenuMapper;
import com.powernode.service.SysMenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-07 10:00
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public int deleteByPrimaryKey(Long menuId) {
        return sysMenuMapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public int insert(SysMenu record) {
      return   sysMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(SysMenu record) {
        //得到菜单类型
        Integer menuType = record.getMenuType();
        //得到子节点
        Long parentId = record.getParentId();
        //声明一个影响行
        int rowAf = 0;
        //进行自旋解决并发
        while (true) {
            //通过类型与子节点查询最大的id 有可能为空，以parentId编号
            Long maxMenuId = this.sysMenuMapper.queryMaxMenuIdByMenuType(menuType, parentId);
            if (maxMenuId == null) {
                //如果是目录则 set id从1开始
                if (menuType.equals(StateEnums.MENU_M.getCode())) {
                    record.setMenuId(1L);
                } else if (menuType.equals(StateEnums.MENU_C.getCode())) {
                    //如果是菜单
                    record.setMenuId(Long.valueOf(parentId + "01"));
                }
            } else {
                record.setMenuId(maxMenuId + 1);
            }
            try {
                rowAf = sysMenuMapper.insertSelective(record);
                break;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
            return rowAf;

    }

    @Override
    public SysMenu selectByPrimaryKey(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKey(record);
    }


    /**
     * 查询所有的菜单权限
     *
     * @param sysMenu
     * @param currentUser
     * @return
     */

    @Override
    public List<SysMenu> queryAllMenuList(SysMenu sysMenu, SysUser currentUser) {
        List<SysMenu> menuList = null;
        //判断用户类型,管理员查询所有菜单权限
        //普通用户 ,更具用户ID查询拥有的菜单和权限
        if (CoreConstant.USER_TYPE_ADMIN.equals(currentUser.getUserType())) {
            menuList = this.sysMenuMapper.queryAllMenuList(sysMenu);
        } else {
            menuList = this.sysMenuMapper.selectMenuListByUserId(sysMenu, currentUser.getUserId());
        }
        return menuList;
    }

    @Override
    public Integer queryChildrenCountByMenuId(Long menuId) {
        return this.sysMenuMapper.queryChildrenCountByMenuId(menuId);
    }

    @Override
    public List<SysMenu> queryAllMenuListMC(SysUser currentUser) {
        List<SysMenu> menuList = null;
        //判断用户类型,管理员查询所有菜单权限
        //普通用户 ,更具用户ID查询拥有的菜单和权限
        if (CoreConstant.USER_TYPE_ADMIN.equals(currentUser.getUserType())) {
            menuList = this.sysMenuMapper.selectMenuListMC();
        } else {
            menuList = this.sysMenuMapper.selectMenuMCByUserId(currentUser.getUserId());
        }
        return menuList;
    }


}
