package com.powernode.service.impl;

import com.powernode.common.Page;
import com.powernode.enums.StateEnums;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.mapper.SysRoleMapper;
import com.powernode.domain.SysRole;
import com.powernode.service.SysRoleService;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-08 11:06
 */
@Service
public class SysRoleServiceImpl extends BaseServiceIml<SysRole> implements SysRoleService{

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId) {
   //根据Id把角色查询出
        SysRole role = this.sysRoleMapper.selectByPrimaryKey(roleId);
        role.setDelFlag(StateEnums.DELETED.getCode());
        //根据角色id删除角色和菜单
        this.sysRoleMapper.deleteRoleMenuByRoleId(roleId);
        //根据角色id删除用户和菜单数据
        this.sysRoleMapper.deleteUserRoleByRoleId(roleId);
        //进行返回
        return sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int insert(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRole record) {
        return sysRoleMapper.insertSelective(record);
    }

    @Override
    public SysRole selectByPrimaryKey(Long roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRole record) {
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<SysRole> getByPage(Page<SysRole> page) {
        com.github.pagehelper.Page<SysRole> startPage = this.startPage(page.getPageNumber(), page.getPageSize());
        List<SysRole> sysRoleList=this.sysRoleMapper.queryAllRole(page.getParams());
        //取出startPage里面内容放到page里面
        page.setTotalCount(startPage.getTotal());
        page.setList(sysRoleList);
        return page;
    }

    /**
     * 根据角色ID查询当前角色拥有的权限菜单
     * @param roleId
     * @return
     */
    @Override
    public List<Long> queryCurrentRoleHasMenuIdsByRoleId(Long roleId) {
        return this.sysRoleMapper.queryCurrentRoleHasMenuIdsByRoleId(roleId);
    }

    @Override
    public void saveRoleMenu(Long roleId, Long[] menuIds) {
        //根据角色ID删除之前 分配的角色sys_role_menu
        this.sysRoleMapper.deleteRoleMenuByRoleId(roleId);
        //进行重新保存
        if(menuIds != null && menuIds.length >0){
            // for循环每次会进行遍历时间复杂度为（o(n)）
            // for (Long menuId : menuIds) {
            // this.sysRoleMapper.saveRoleMenu(roleId,menuId);
           // }
            //进行优化,利用sql批量插入
            this.sysRoleMapper.saveRoleMenuBatch(roleId,menuIds);

        }
    }

    /**
     * 查询所有角色不分页
     * @return
     */
    @Override
    public List<SysRole> queryAllRoles() {
        Map<String ,Object> params = new HashMap<>() ;
        params.put("status",0);
        List<SysRole> sysRoleList = this.sysRoleMapper.queryAllRole(params);
        return sysRoleList;
    }
    /**
     * 根据用户ID查询用户已拥有的角色IDS
     * @param userId
     * @return
     */
    @Override
    public List<Long> queryRoleIdsByUserId(Long userId) {
        return this.sysRoleMapper.queryRoleIdsByUserId(userId);
    }
    /**
     * 保存用户和角色之间的关系
     * @param userId
     * @param roleIds
     */
    @Override
    public void saveUserRoles(Long userId, Long[] roleIds) {
        //先根据userId删除用户与角色的关系数据
        this.sysRoleMapper.deleteUserRoleByUserId(userId);
        if (roleIds !=null && roleIds.length >0){
            //批量保存
            this.sysRoleMapper.saveUserRoles(userId,roleIds);
        }

    }

}
