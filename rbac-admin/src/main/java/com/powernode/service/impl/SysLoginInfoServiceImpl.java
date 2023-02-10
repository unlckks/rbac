package com.powernode.service.impl;

import com.powernode.common.Page;
import com.powernode.domain.SysRole;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysLoginInfo;
import com.powernode.mapper.SysLoginInfoMapper;
import com.powernode.service.SysLoginInfoService;

import java.util.List;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-10 14:15
 */
@Service
public class SysLoginInfoServiceImpl extends BaseServiceIml implements SysLoginInfoService{

    @Resource
    private SysLoginInfoMapper sysLoginInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long infoId) {
        return sysLoginInfoMapper.deleteByPrimaryKey(infoId);
    }

    @Override
    public int insert(SysLoginInfo record) {
        return sysLoginInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(SysLoginInfo record) {
        return sysLoginInfoMapper.insertSelective(record);
    }

    @Override
    public SysLoginInfo selectByPrimaryKey(Long infoId) {
        return sysLoginInfoMapper.selectByPrimaryKey(infoId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysLoginInfo record) {
        return sysLoginInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysLoginInfo record) {
        return sysLoginInfoMapper.updateByPrimaryKey(record);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<SysLoginInfo> getByPage(Page<SysLoginInfo> page) {
        com.github.pagehelper.Page<SysLoginInfo> startPage = this.startPage(page.getPageNumber(), page.getPageSize());
        List<SysLoginInfo>   SysLoginInfo =this.sysLoginInfoMapper.queryAllSysLoginInfo(page.getParams());
        //取出startPage里面内容放到page里面
        page.setTotalCount(startPage.getTotal());
        page.setList(SysLoginInfo);
        return page;

    }

}
