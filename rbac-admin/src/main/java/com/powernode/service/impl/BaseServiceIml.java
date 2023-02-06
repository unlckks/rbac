package com.powernode.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 封装分页
 * @Author: MingYun
 * @Date: 2023-02-04 11:38
 */
public class BaseServiceIml <T> {
    /**
     * 开启分页
     *
     */
    public Page<T> startPage(Integer pageNumber ,Integer pageSize){
        return PageHelper.startPage(pageNumber,pageSize);
    }
}

