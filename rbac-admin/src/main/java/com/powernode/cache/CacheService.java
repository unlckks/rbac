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
    private SysDictTypeMapper dictTypeMapper;

    @Resource
    private SysDictDataMapper dictDataMapper;

    /**
     * 模拟缓存数据
     */
    private  Map<String, List<SysDictData>> redis=new HashMap<>();

    /**
     * 同步所有缓存
     */
    public void refreshCache(){
        //查询所有可用的字典类型
        Map<String, Object> params=new HashMap<>();
        params.put("status", StateEnums.ENABLED.getCode());//启用状态的
        List<SysDictType> dictTypeList = this.dictTypeMapper.queryAllDictType(params);
        //循环所有的类型再根据类型查询数据
        for (SysDictType sysDictType : dictTypeList) {
            List<SysDictData> dictDataList = this.dictDataMapper.queryDictDataByDictType(sysDictType.getDictType());
            //把dictType做为key把dictDataList作为值放到模拟map里面
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
     * 清空缓存
     */
    public void clear(){
        this.redis.clear();
    }
}

