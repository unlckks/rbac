package com.powernode.mapper;

import com.powernode.domain.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: MingYun
 * @Date: 2023-02-05 09:20
 */
public interface SysDictTypeMapper {
    int deleteByPrimaryKey(Long dictId);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

    /**
     * 查询所有的字典
     *
     * @param params
     * @return
     */
    List<SysDictType> queryAllDictType(@Param("params") Map<String, Object> params);
}