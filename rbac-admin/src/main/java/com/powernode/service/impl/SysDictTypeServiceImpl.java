package com.powernode.service.impl;

import com.powernode.cache.CacheService;
import com.powernode.common.Page;
import com.powernode.mapper.SysDictDataMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysDictType;
import com.powernode.mapper.SysDictTypeMapper;
import com.powernode.service.SysDictTypeService;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-05 09:20
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceIml<SysDictType>  implements SysDictTypeService{

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    @Resource
    private CacheService cacheService;

    @Resource
    private SysDictDataMapper sysDictDataMapper ;
    @Override
    public int deleteByPrimaryKey(Long dictId) {
        cacheService.clear();
        SysDictType sysDictType = this.sysDictTypeMapper.selectByPrimaryKey(dictId);
        if (null !=sysDictType){
            this.sysDictDataMapper.deleteDictDataByDictType(sysDictType.getDictType());
        }
        return sysDictTypeMapper.deleteByPrimaryKey(dictId);
    }

    @Override
    public int insert(SysDictType record) {
        cacheService.clear();
        return sysDictTypeMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDictType record) {
        cacheService.clear();
        return sysDictTypeMapper.insertSelective(record);
    }

    @Override
    public SysDictType selectByPrimaryKey(Long dictId) {
        cacheService.clear();
        return sysDictTypeMapper.selectByPrimaryKey(dictId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictType record) {
        cacheService.clear();
        return sysDictTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDictType record) {
        cacheService.clear();
        return sysDictTypeMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<SysDictType> getByPage(Page<SysDictType> page) {
        com.github.pagehelper.Page<SysDictType> startPage = this.startPage(page.getPageNumber(), page.getPageSize());
        //查询所有的字典
        List<SysDictType> dictTypesList =this.sysDictTypeMapper.queryAllDictType(page.getParams());
         page.setTotalCount(startPage.getTotal());
         page.setList(dictTypesList);

        return page;
    }

    @Override
    public void refreshCache() {
        cacheService.refreshCache();

    }

}
