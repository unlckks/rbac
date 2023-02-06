package com.powernode.service.impl;

import com.powernode.enums.StateEnums;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.mapper.SysDeptMapper;
import com.powernode.domain.SysDept;
import com.powernode.service.SysDeptService;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-06 16:38
 */
@Service
public class SysDeptServiceImpl implements SysDeptService{
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public int deleteByPrimaryKey(Long deptId) {
        SysDept sysDept = this.sysDeptMapper.selectByPrimaryKey(deptId);
        if(sysDept!=null){
            sysDept.setDelFlag(StateEnums.DELETED.getCode());
            return this.sysDeptMapper.updateByPrimaryKey(sysDept);
        }
        return 0;
    }

    @Override
    public int insert(SysDept record) {
        return sysDeptMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDept record) {
        return sysDeptMapper.insertSelective(record);
    }

    @Override
    public SysDept selectByPrimaryKey(Long deptId) {
        return sysDeptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDept record) {
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDept record) {
        return sysDeptMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysDept> queryAllDeptList(SysDept sysDept) {
        return this.sysDeptMapper.queryAllDeptList(sysDept);
    }

    /**
     * 根据deptId查询这个deptId下面子节点个数
     * @param deptId
     * @return
     */
    @Override
    public Integer queryChildrenCountByDeptId(Long deptId) {
        return this.sysDeptMapper.queryChildrenCountByDeptId(deptId);
    }
}
