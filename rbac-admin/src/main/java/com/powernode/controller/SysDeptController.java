package com.powernode.controller;

import cn.hutool.core.date.DateTime;
import com.powernode.common.Result;
import com.powernode.domain.SysDept;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 部门管理控制器
 *
 * @Author: MingYun
 * @Date: 2023-02-06 14:47
 */
@RestController
@RequestMapping("sysDept")
public class SysDeptController extends BaseController {
    @Resource
    private SysDeptService deptService;

    /**
     * 查询所有部门
     * GET http://127.0.0.1:8080/sysDept/listDept
     * 接口ID：60129606
     * 接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
     */
    @GetMapping("listDept")
    public Result listDept(SysDept sysDept) {
        List<SysDept> sysDeptList = deptService.queryAllDeptList(sysDept);
        return new Result(sysDeptList);
    }

    /**
     * 添加部门
     * POST http://127.0.0.1:8080/sysDept/save
     * 接口ID：60129607
     * 接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
     */
    @PostMapping("save")
    public Result save(@RequestBody SysDept dept) {
        //创建时间
        dept.setCreateTime(new Date());
        //创建人
        dept.setCreateBy(getUsername());
        this.deptService.insertSelective(dept);
        return new Result("添加成功");
    }

    /**
     * 修改部门
     * PUT http://127.0.0.1:8080/sysDept/update
     * 接口ID：60129609
     * 接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
     */
    @PutMapping("update")
    public Result update(@RequestBody SysDept dept) {
        //修改时间
        dept.setUpdateTime(new Date());
        //修改人
        dept.setUpdateBy(getUsername());
        this.deptService.updateByPrimaryKeySelective(dept);
        return new Result("修改成功");
    }

    /**
     * 根据ID查询部门
     * GET http://127.0.0.1:8080/sysDept/get/{deptId}
     * 接口ID：60129610
     * 接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
     */
    @GetMapping("get/{deptId}")
    public  Result get(@PathVariable Long deptId){
        SysDept sysDept = this.deptService.selectByPrimaryKey(deptId);
        return  new Result(sysDept);

    }
    /**
     * 删除部门
     *   DELETE http://127.0.0.1:8080/sysDept/delete/{deptId}
     *   接口ID：60129608
     *   接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
     */
    @DeleteMapping ("delete/{deptId}")
    public  Result delete(@PathVariable Long deptId){
        //创建子节点数量根据deptId查询看是否有parent节点
        Integer  count =  this.deptService.queryChildrenCountByDeptId(deptId);
        //判断子节点是否大于0
        if (count > 0){
            return new Result (ResultEnums.ERROR,"该部门下有子节点,请删除子节点啊");
        }
        this.deptService.deleteByPrimaryKey(deptId);
        return new Result("删除成功！") ;
    }
}
