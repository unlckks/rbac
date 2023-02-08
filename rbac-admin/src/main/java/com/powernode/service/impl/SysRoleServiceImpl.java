package com.powernode.service.impl;

import com.powernode.common.Page;
import com.powernode.enums.StateEnums;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.mapper.SysRoleMapper;
import com.powernode.domain.SysRole;
import com.powernode.service.SysRoleService;

import java.util.List;

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
        this.sysRoleMapper.deleteRoleUserByRoleId(roleId);
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
            for (Long menuId : menuIds) {
                this.sysRoleMapper.saveRoleMenu(roleId,menuId);
            }

        }
    }

}
