package com.powernode.mapper;

import com.powernode.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: MingYun
 * @Date: 2023-02-04 14:22
 */
public interface SysDictDataMapper {
    int deleteByPrimaryKey(Long dictCode);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long dictCode);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);

    /**
     * 根据字典类型查询字典里面的数据
     *
     * @param dictType
     * @return
     */
    List<SysDictData> queryDictDataByDictType(@Param("dictType") String dictType);

    /**
     * 根据字典类型删数据
     *
     * @param dictType
     */
    void deleteDictDataByDictType(@Param("dictType") String dictType);
}