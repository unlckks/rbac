package com.powernode.cache;

import com.powernode.domain.SysDictData;
import com.powernode.domain.SysDictType;
import com.powernode.enums.StateEnums;
import com.powernode.mapper.SysDictDataMapper;
import com.powernode.mapper.SysDictTypeMapper;
import com.powernode.service.SysDictTypeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: MingYun
 * @Date: 2023-02-06 10:46
 */
@Component
public class CacheService {

    @Resource
    private SysDictTypeMapper dictTypeMapper ;

    @Resource
    private  SysDictDataMapper dictDataMapper ;

    /**
     * 模拟缓存数据
     */
    private Map<String, List<SysDictData>> redis=new HashMap<>();

    /**
     * 同步所有缓存
     */
     public void refreshCache(){
      HashMap<String, Object> params = new HashMap<>();
      //启用状态
      params.put("status", StateEnums.ENABLED.getCode());
      //查询所有类型
      List<SysDictType> dictTypesList = this.dictTypeMapper.queryAllDictType(params);
      //循环所有的类型根据类型查询数据
      for (SysDictType sysDictType : dictTypesList) {
          List<SysDictData> dictDataList = this.dictDataMapper.queryDictDataByDictType(sysDictType.getDictType());
          //把dictType变为key放入模拟缓存 map里面
          redis.put(sysDictType.getDictType(),dictDataList);
         }
    }
    /**
     * 根据类型查询缓存数据
     */
    public List<SysDictData> getDictDataByKey(String dictType){
     return redis.get(dictType);
 }
    /**
     *放数据
     */
    public void setDictDataByDictType(String dictType,List<SysDictData> dictDataList){
        redis.put(dictType,dictDataList);
    }
    /**
     * 清空
     */
    public void  clear(){
     //进行清空
     this.redis.clear();
 }
}

