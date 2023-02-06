package com.powernode.mapper;

import com.powernode.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-06 16:38
 */
public interface SysDeptMapper {
    int deleteByPrimaryKey(Long deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    /**
     * 查询所有部门
     *
     * @param sysDept
     * @return
     */
    List<SysDept> queryAllDeptList(@Param("dept") SysDept sysDept);

    /**
     * 根据deptId查询这个deptId下面子节点个数
     *
     * @param deptId
     * @return
     */
    Integer queryChildrenCountByDeptId(@Param("deptId") Long deptId);
}