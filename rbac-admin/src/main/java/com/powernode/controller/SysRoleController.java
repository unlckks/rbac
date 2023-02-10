package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysRole;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-08 11:06
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController extends BaseController{
    @Resource
    private SysRoleService roleService;

    /**
     * 分页查询角色
     *   POST http://127.0.0.1:8080/sysRole/getByPage
     *   接口ID：60129618
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129618
     */
    @PostMapping("getByPage")
    public Result getByPage(@RequestBody Page<SysRole> page){
        page=this.roleService.getByPage(page);
        return new Result(page);
    }
    /**
     * 添加角色
     *   POST http://127.0.0.1:8080/sysRole/save
     *   接口ID：60129619
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129619
     */
    @PostMapping("save")
    public Result save(@RequestBody SysRole role){
        role.setCreateTime(new Date());
        role.setCreateBy(getUsername());
        //设置flag为没有删除
        role.setDelFlag(StateEnums.NOT_DELETED.getCode());
        //进行添加
        this.roleService.insertSelective(role);
        return new Result("添加成功") ;

    }
    /**
     *修改角色
     *   PUT http://127.0.0.1:8080/sysRole/update
     *   接口ID：60129620
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129620
     */
    @PutMapping("update")
    public Result update(@RequestBody  SysRole sysRole){
        //是谁修改的
        sysRole.setCreateBy(getUsername());
        //什么时候
        sysRole.setCreateTime(new Date());
        this.roleService.updateByPrimaryKeySelective(sysRole);
        return new Result("修改成功");
    }


    /**
     *删除角色
     *   DELETE http://127.0.0.1:8080/sysRole/delete/{roleId}
     *   接口ID：60129621
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129621
     */
    @DeleteMapping("delete/{roleId}")
    public  Result delete(@PathVariable Long roleId){
        this.roleService.deleteByPrimaryKey(roleId);
        return new Result("删除成功");

    }

    /**
     * 根据ID查询角色
     *   GET http://127.0.0.1:8080/sysRole/get/{roleId}
     *   接口ID：60129622
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129622
     */
    @GetMapping("get/{roleId}")
        public Result get(@PathVariable Long roleId){
        SysRole Role = this.roleService.selectByPrimaryKey(roleId);
        return  new Result(Role) ;
        }


    /**
     * 根据角色ID查询当前角色拥有的权限菜单
     *   GET http://127.0.0.1:8080/sysRole/getCurrentRoleHasMenuIdsByRoleId/{roleId}
     *   接口ID：60129623
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129623
     */
    @GetMapping("getCurrentRoleHasMenuIdsByRoleId/{roleId}")
    public  Result getCurrentRoleHasMenuIdsByRoleId(@PathVariable Long roleId){
        if (roleId == 0){
                  return new Result(ResultEnums.ERROR,"角色不能为空");

        }
        List<Long> role =this.roleService.queryCurrentRoleHasMenuIdsByRoleId(roleId);
       return  new Result(role);
    }
    /**
     * 保存角色菜单之间的关系
     *   POST http://127.0.0.1:8080/sysRole/saveRoleMenu
     *   接口ID：60129624
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129624
     */
    @PostMapping("saveRoleMenu")
    public  Result saveRoleMenu( Long roleId, Long[] menuIds){
        if(roleId == null ){
            return new Result(ResultEnums.ERROR,"角色不能为空");
        }
        this.roleService.saveRoleMenu(roleId,menuIds);
        return new Result("保存成功");
    }

    /**
     * 查询所有角色不分页
     *   GET http://127.0.0.1:8080/sysRole/
     *   接口ID：60129631
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129631
     */
    @GetMapping("getAllRoles")
    public Result getAllRoles(){
      List<SysRole>  roles= this.roleService.queryAllRoles();
         return  new Result(roles);
    }

    /**
     * 根据用户ID查询用户已拥有的角色IDS
     *   GET http://127.0.0.1:8080/sysRole/getRoleIdsByUserId/{userId}
     *   接口ID：60129632
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129632
     */
       @GetMapping("getRoleIdsByUserId/{userId}")
    public  Result getRoleIdsByUserId(@PathVariable Long userId){
           List<Long> roleId =this.roleService.queryRoleIdsByUserId(userId);
           return  new Result(roleId);
       }
    /**
     * 保存用户和角色之间的关系
     *   POST http://127.0.0.1:8080/sysRole/saveUserRoles
     *   接口ID：60129633
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129633
     */
    @PostMapping("saveUserRoles")
    public  Result saveUserRoles (Long userId ,Long[] roleIds){
        //判断用户id 为null
        if (userId == null){
            return new Result(ResultEnums.ERROR,"用户编号不能为空");
        }
            this.roleService.saveUserRoles(userId,roleIds);
        return new Result( ) ;
    }

}
