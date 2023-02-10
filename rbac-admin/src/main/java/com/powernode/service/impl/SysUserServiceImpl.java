package com.powernode.service.impl;

import com.powernode.common.Page;
import com.powernode.enums.StateEnums;
import com.powernode.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysUser;
import com.powernode.mapper.SysUserMapper;
import com.powernode.service.SysUserService;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-09 15:59
 */
@Service
public class SysUserServiceImpl extends  BaseServiceIml<SysUser> implements SysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        SysUser user = this.sysUserMapper.selectByPrimaryKey(userId);
        if(user==null){
            return 0;
        }
        user.setDelFlag(StateEnums.DELETED.getCode());
        //删除当前用户ID对应的角色关系
        this.sysRoleMapper.deleteUserRoleByUserId(user.getUserId());
        return sysUserMapper.updateByPrimaryKey(user);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public SysUser login(String username, String password) {
        return this.sysUserMapper.login(username,password);
    }

    @Override
    public List<String> queryAllPermissions() {
        return this.sysUserMapper.queryAllPermissions();
    }

    @Override
    public List<String> queryPermissionsByUserId(Long userId) {
        return sysUserMapper.queryPermissionsByUserId(userId);
    }

    /**
     * 分页查询用户
     * @param page
     * @return
     */
    @Override
    public Page<SysUser> getByPage(Page<SysUser> page) {
        com.github.pagehelper.Page<SysUser> sysUserPage = this.startPage(page.getPageNumber(), page.getPageSize());
        List<SysUser> userList=this.sysUserMapper.queryAllUser(page.getParams());
        page.setTotalCount(sysUserPage.getTotal());
        page.setList(sysUserPage.getResult());
        return page;
    }

    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    @Override
    public SysUser queryUserByUserName(String userName) {
        return this.sysUserMapper.queryUserByUserName(userName);
    }
}
