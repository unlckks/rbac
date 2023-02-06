package com.powernode.service;

import com.powernode.domain.SysDept;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-06 16:38
 */
public interface SysDeptService{


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
        List<SysDept> queryAllDeptList(SysDept sysDept);

        /**
         * 根据deptId查询这个deptId下面子节点个数
         * @param deptId
         * @return
         */
        Integer queryChildrenCountByDeptId(Long deptId);

}
