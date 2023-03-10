package com.powernode.service.impl;

import com.powernode.cache.CacheService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysDictData;
import com.powernode.mapper.SysDictDataMapper;
import com.powernode.service.SysDictDataService;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-04 14:22
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService{

    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private CacheService cacheService ;
    @Override
    public int deleteByPrimaryKey(Long dictCode) {
        cacheService.clear();
        return sysDictDataMapper.deleteByPrimaryKey(dictCode);
    }

    @Override
    public int insert(SysDictData record) {
        cacheService.clear();
        return sysDictDataMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDictData record) {
        cacheService.clear();
        return sysDictDataMapper.insertSelective(record);
    }

    @Override
    public SysDictData selectByPrimaryKey(Long dictCode) {
        cacheService.clear();
        return sysDictDataMapper.selectByPrimaryKey(dictCode);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictData record) {
        cacheService.clear();
        return sysDictDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDictData record) {
        cacheService.clear();
        return sysDictDataMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysDictData> queryDictDataByDictType(String dictType) {
        //根据类型到缓存里面取
        List<SysDictData> dictDataByKey = cacheService.getDictDataByKey(dictType);
        if(dictDataByKey!=null){
            return dictDataByKey;
        }
        //说明缓存里面没有 要查询数据
        List<SysDictData> dictDataList = this.sysDictDataMapper.queryDictDataByDictType(dictType);
        //把数据再放到缓存里面
        cacheService.setDictDataByDictType(dictType,dictDataList);
        return dictDataList;
    }
}
