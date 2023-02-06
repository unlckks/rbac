package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysDictType;
import com.powernode.service.SysDictTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: MingYun
 * @Date: 2023-02-05 09:20
 */
@RestController
@RequestMapping("sysDictType")
public class SysDictTypeController extends BaseController {

        @Resource
        private SysDictTypeService sysDictTypeService ;

        /**
         * 分页查询字典类型数据
         *   POST http://127.0.0.1:8080/sysDictType/getByPage
         *   接口ID：60129646
         *   接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
         */
        @PostMapping("getByPage")
        public Result getByPage(@RequestBody Page<SysDictType> page){
            page =  sysDictTypeService.getByPage(page);
            return new Result (page);
        }
        /**
         * 添加字典类型
         *   POST http://127.0.0.1:8080/sysDictType/save
         *   接口ID：60129648
         *   接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
         *
         */
        @PostMapping("save")
        public  Result save(@RequestBody SysDictType dictType){
            //获取当前用户
            dictType.setCreateBy(getUsername());
            //获取添加用户时间
            dictType.setCreateTime(new Date());
            sysDictTypeService.insertSelective(dictType);
            return  new Result("添加成功");
        }
        /**
         * 根据ID查询字典类型
         *   GET http://127.0.0.1:8080/sysDictType/get/{dictId}
         *   接口ID：60129649
         *   接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
         */
        @GetMapping("get/{dictId}")
        public  Result get(@PathVariable Long dictId){
            SysDictType dictType = this.sysDictTypeService.selectByPrimaryKey(dictId);
            //直接返回结果——类中有成功
            return  new Result(dictType);
        }

        /**
         * 修改字典类型
         *    PUT http://127.0.0.1:8080/sysDictType/update
         *        接口ID：60129650
         *       接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
         * @RequestBody用来返回jason字符串
         * @param dictType
         * @return
         */
        @PutMapping("update")
        public  Result update(@RequestBody SysDictType dictType){
            //是谁修改
            dictType.setDictType(getUsername());
            //什么时候修改
            dictType.setUpdateTime(new Date());
            this.sysDictTypeService.updateByPrimaryKey(dictType);;
            return new Result("修改成功");
        }
        /**
         * 删除字典类型
         *   DELETE http://127.0.0.1:8080/sysDictType/delete/{dictId}
         *   接口ID：60129652
         *   接口地址：https://www.apifox.cn/link/project/{{projectId}}/apis/api-{{id}}
         *   pathVariable 用来绑定占位符{} url
         */
        @DeleteMapping("delete/{dictId}")
        public  Result delete( @PathVariable Long dictId){
            this.sysDictTypeService.deleteByPrimaryKey(dictId);
            return new Result("删除成功");
        }
        @GetMapping("refreshCache")
    public Result refreshCache(){
            this.sysDictTypeService.refreshCache();
            return  new Result() ;
        }
    }

