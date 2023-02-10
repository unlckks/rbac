package com.powernode.controller;


import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysUserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: MingYun
 * @Date: 2023-02-08 11:06
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController extends BaseController{
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询用户
     * POST http://127.0.0.1:8080/sysUser/getByPage
     * 接口ID：43445089
     * 接口地址：https://www.apifox.cn/web/project/1739100/apis/api-43445089
     * @param page
     * @return
     */
    @PostMapping("getByPage")
    public Result getByPage(@RequestBody Page<SysUser> page){
        page = sysUserService.getByPage(page);
        return new Result<>(page);
    }

    /**
     添加用户
     POST http://127.0.0.1:8080/sysUser/save
     接口ID：43445090
     接口地址：https://www.apifox.cn/web/project/1739100/apis/api-43445090
     */
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        //根据用户名查询用户对象
        SysUser user=this.sysUserService.queryUserByUserName(sysUser.getUserName());
        if(user !=null){
            if(user.getDelFlag().equals(StateEnums.DELETED.getCode())){
                //说明这个用户之前被删除过
                return new Result(ResultEnums.USER_EXISTS,"有被删除的用户使用这个登录名，当前添加的用户不能使用");
            }
            return new Result(ResultEnums.USER_EXISTS);
        }
        //设置用户类型
        sysUser.setUserType(StateEnums.USER_TYPE_NORMAL.getCode());
        //设置头像
        sysUser.setAvatar(null);
        //设置默认密码
        sysUser.setPassword(DigestUtils.md5DigestAsHex(CoreConstant.DEFAULT_PASSWORD.getBytes()));
        //设置创建人
        sysUser.setCreateBy(getUsername());
        //设置创建时间
        sysUser.setCreateTime(new Date());
        sysUserService.insertSelective(sysUser);
        return new Result("添加成功");
    }


    /**
     * 根据ID查询用户
     * GET http://127.0.0.1:8080/sysUser/get/{userId}
     * 接口ID：43445093
     * 接口地址：https://www.apifox.cn/web/project/1739100/apis/api-43445093
     */
    @GetMapping("get/{userId}")
    public Result get(@PathVariable Long userId){
        SysUser sysUser = this.sysUserService.selectByPrimaryKey(userId);
        return new Result(sysUser);
    }


    /**
     * 修改用户
     * PUT http://127.0.0.1:8080/sysUser/update
     * 接口ID：43445091
     * 接口地址：https://www.apifox.cn/web/project/1739100/apis/api-43445091
     */
    @PutMapping("update")
    public Result update(@RequestBody SysUser sysUser){
        //根据用户名查询用户对象
        SysUser user=this.sysUserService.queryUserByUserName(sysUser.getUserName());
        if(user!=null&&!user.getUserId().equals(sysUser.getUserId())){
            return new Result(ResultEnums.USER_EXISTS);
        }
        sysUser.setUpdateBy(getUsername());
        sysUser.setUpdateTime(new Date());
        this.sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result("修改成功");
    }

    /**
     * 删除用户
     * DELETE http://127.0.0.1:8080/sysUser/delete/{userId}
     * 接口ID：43445092
     * 接口地址：https://www.apifox.cn/web/project/1739100/apis/api-43445092
     */
    @DeleteMapping("delete/{userId}")
    public Result delete(@PathVariable Long userId){
        this.sysUserService.deleteByPrimaryKey(userId);
        return new Result("删除成功");
    }
    /**
     * 重置用户密码
     * GET http://127.0.0.1:8080/sysUser/resetPwd/{userId}
     * 接口ID：43445103
     * 接口地址：https://www.apifox.cn/web/project/1739100/apis/api-43445103
     */
    @GetMapping("resetPwd/{userId}")
    public Result resetPwd(@PathVariable Long userId){
        SysUser sysUser = this.sysUserService.selectByPrimaryKey(userId);
        if(null==sysUser){
            return new Result(ResultEnums.USER_NOT_FOUND);
        }
        sysUser.setPassword(DigestUtils.md5DigestAsHex(CoreConstant.DEFAULT_PASSWORD.getBytes()));
        //修改
        this.sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result();
    }












}
