package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysLoginInfo;
import com.powernode.domain.SysRole;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysLoginInfoService ;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-08 11:06
 */
@RestController
@RequestMapping("sysLoginInfo")
public class SysLoginInfoController extends BaseController{
    @Resource
    private SysLoginInfoService  sysLoginInfoService;

    /**
     * 分页查询登陆日志
     *   POST http://127.0.01:8080/sysLoginInfo/getPage
     *   接口ID：60129634
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129634
     */
    @RequestMapping(value = "/getByPage" , method = RequestMethod.POST)
    public Result getByPage(@RequestBody Page<SysLoginInfo> page){
        page=this.sysLoginInfoService.getByPage(page);
        return new Result(page);
    }




    /**
     *删除登陆日志
     *   DELETE http://127.0.01:8080/sysLoginInfo/delete/{id}
     *   接口ID：60129635
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129635
     */
    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    public  Result delete(@PathVariable Long  id){
        this.sysLoginInfoService.deleteByPrimaryKey(id);
        return new Result("删除成功");

    }

    /**
     * 批量删除登陆日志
     *   DELETE http://127.0.01:8080/sysLoginInfo/batchDelete/{ids}
     *   接口ID：60129636
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129636
     */
   /*@RequestMapping(value = "batchDelete/{ids}",method = RequestMethod.DELETE)
        public Result get(@PathVariable Long[] ids) {
        if (ids == null && ids.length == 0) {
            return new Result(ResultEnums.ERROR);
        }
     sysLoginInfoService.deleteByPrimaryKey(ids)
    }*/
    /**
     * 清空登陆日志
     *   DELETE http://127.0.0.1:8080/sysLoginInfo/cleanLogininfor
     *   接口ID：60129637
     *   接口地址：https://www.apifox.cn/link/project/2245479/apis/api-60129637
     */
   /*  @DeleteMapping("cleanLogininfor")
    public  Result saveRoleMenu( Long roleId, Long[] menuIds){
        if(roleId == null ){
            return new Result(ResultEnums.ERROR,"角色不能为空");
        }
        this.sysLoginInfoService.saveRoleMenu(roleId,menuIds);
        return new Result("保存成功");
    }*/

}
