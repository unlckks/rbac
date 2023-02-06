package com.powernode.service;

import com.powernode.domain.SysDictData;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-04 14:22
 */
public interface SysDictDataService{


    int deleteByPrimaryKey(Long dictCode);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long dictCode);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);

        List<SysDictData> queryDictDataByDictType(String dictType);
    }
