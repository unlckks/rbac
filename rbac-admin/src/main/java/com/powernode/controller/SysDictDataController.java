package com.powernode.controller;


import com.powernode.common.Result;
import com.powernode.domain.SysDictData;
import com.powernode.service.SysDictDataService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-04  11:27
 */
@RestController
@RequestMapping("sysDictData")
public class SysDictDataController extends BaseController  {

    @Resource
    private SysDictDataService sysDictDataService;


    /**
     添加字典数据
     POST http://127.0.0.1:8080/sysDictData/save
     接口ID：43445175
     接口地址：https://www.apifox.cn/web/project/{{projectId}}/apis/api-{{id}}
     */
    @PostMapping("save")
    public Result save(@RequestBody SysDictData dictType){
        dictType.setCreateBy(getUsername());
        dictType.setCreateTime(new Date());
        sysDictDataService.insertSelective(dictType);
        return new Result("添加成功");
    }


    /**
     * 根据ID查询字典数据
     * GET http://127.0.0.1:8080/sysDictData/get/{dictCode}
     * 接口ID：43445177
     * 接口地址：https://www.apifox.cn/web/project/{{projectId}}/apis/api-{{id}}
     */
    @GetMapping("get/{dictCode}")
    public Result get(@PathVariable Long dictCode){
        SysDictData dictType = this.sysDictDataService.selectByPrimaryKey(dictCode);
        return new Result(dictType);
    }


    /**
     * 修改字典数据
     * PUT http://127.0.0.1:8080/sysDictData/update
     * 接口ID：43445176
     * 接口地址：https://www.apifox.cn/web/project/{{projectId}}/apis/api-{{id}}
     */
    @PutMapping("update")
    public Result update(@RequestBody SysDictData dictType){
        dictType.setUpdateBy(getUsername());
        dictType.setUpdateTime(new Date());
        this.sysDictDataService.updateByPrimaryKeySelective(dictType);
        return new Result("修改成功");
    }

    /**
     * 删除字典数据
     * DELETE http://127.0.0.1:8080/sysDictData/delete/{dictCode}
     * 接口ID：43445178
     * 接口地址：https://www.apifox.cn/web/project/{{projectId}}/apis/api-{{id}}
     */
    @DeleteMapping("delete/{dictCode}")
    public Result delete(@PathVariable Long dictCode){
        this.sysDictDataService.deleteByPrimaryKey(dictCode);
        return new Result("删除成功");
    }

    /**
     * 根据字典类型获取字典数据
     * GET http://127.0.0.1:8080/sysDictData/getDictDataByDictType/{dictType}
     * 接口ID：43445179
     * 接口地址：https://www.apifox.cn/web/project/{{projectId}}/apis/api-{{id}}
     */
    @GetMapping("getDictDataByDictType/{dictType}")
    public Result getDictDataByDictType(@PathVariable String dictType){
        List<SysDictData> dictDataList=this.sysDictDataService.queryDictDataByDictType(dictType);
        return new Result(dictDataList);
    }

}
