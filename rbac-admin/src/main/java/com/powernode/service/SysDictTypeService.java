package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysDictType;
    /**
 *  @Author: MingYun
 *  @Date: 2023-02-05 09:20
 */
public interface SysDictTypeService{


    int deleteByPrimaryKey(Long dictId);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

        /**
         * 分页查询字典数据类型
         * @param page
         * @return
         */
        Page<SysDictType> getByPage(Page<SysDictType> page);

        /**
         * 同步数据类型
         */
        void refreshCache();
    }
