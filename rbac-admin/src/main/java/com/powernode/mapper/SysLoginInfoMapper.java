package com.powernode.mapper;

import com.powernode.domain.SysLoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-10 14:15
 */
public interface SysLoginInfoMapper {
    int deleteByPrimaryKey(Long infoId);

    int insert(SysLoginInfo record);

    int insertSelective(SysLoginInfo record);

    SysLoginInfo selectByPrimaryKey(Long infoId);

    int updateByPrimaryKeySelective(SysLoginInfo record);

    int updateByPrimaryKey(SysLoginInfo record);

    /**
     * 分页
     *
     * @param params
     * @return
     */
    List<SysLoginInfo> queryAllSysLoginInfo(@Param("params") Map<String, Object> params)
    ;
}