package com.powernode.controller;

import com.powernode.common.Result;
import com.powernode.domain.SysMenu;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 菜单管理控制器
 *
 * @Author: MingYun
 * @Date: 2023-02-06 14:47
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController extends BaseController {
    @Resource
    private SysMenuService menuService;

    /**
     * 查询所有菜单权限
     *   GET http://127.0.0.1:8080/sysMenu/listMenu
     *   接口ID：60129611
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129611
     */
    @GetMapping("listMenu")
    public Result listMenu(SysMenu sysMenu) {
        List<SysMenu> sysMenuList = this.menuService.queryAllMenuList(sysMenu, getCurrentUser());
        return new Result(sysMenuList);
    }

    /**
     * 添加菜单权限
     *   POST http://127.0.0.1:8080/sysMenu/save
     *   接口ID：60129612
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129612
     */
    @PostMapping("save")
    public Result save(@RequestBody SysMenu menu) {
        //创建时间
        menu.setCreateTime(new Date());
        //创建人
        menu.setCreateBy(getUsername());
        this.menuService.insertSelective(menu);
        return new Result("添加成功");
    }

    /**
     * 修改菜单权限
     *   PUT http://127.0.0.1:8080/sysMenu/update
     *   接口ID：60129613
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129613
     */
    @PutMapping("update")
    public Result update(@RequestBody SysMenu menu) {
        //修改时间
        menu.setUpdateTime(new Date());
        //修改人
        menu.setUpdateBy(getUsername());
        this.menuService.updateByPrimaryKeySelective(menu);
        return new Result("修改成功");
    }

    /**
     * 根据菜单ID查询菜单
     *   GET http://127.0.0.1:8080/sysMenu/get/{menuId}
     *   接口ID：60129615
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129615
     */
    @GetMapping("get/{menuId}")
    public  Result get(@PathVariable Long menuId){
        SysMenu sysMenu = this.menuService.selectByPrimaryKey(menuId);
        return  new Result(sysMenu);

    }
    /**
     * 删除菜单权限
     *   DELETE http://127.0.0.1:8080/sysMenu/delete/{menuId}
     *   接口ID：60129614
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129614
     */
    @DeleteMapping ("delete/{menuId}")
    public  Result delete(@PathVariable Long menuId){
        //创建子节点数量根据menuId查询看是否有parent节点
        Integer  count =  this.menuService.queryChildrenCountByMenuId(menuId);
        //判断子节点是否大于0
        if (count > 0){
            return new Result (ResultEnums.ERROR,"该部门下有子节点,请删除子节点啊");
        }
        this.menuService.deleteByPrimaryKey(menuId);
        return new Result("删除成功！") ;
    }
    /**
     * 只查询菜单和目录
     *   GET http://127.0.0.1:8080/sysMenu/listMenuMC
     *   接口ID：60129616
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129616
     */
    @GetMapping("listMenuMC")
    public  Result listMenuMC(){
        List<SysMenu> sysMenuList = this.menuService.queryAllMenuListMC(getCurrentUser());
        return new Result(sysMenuList);
    }



}
