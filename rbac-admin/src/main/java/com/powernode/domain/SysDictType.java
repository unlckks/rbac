package com.powernode.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-05 09:20
 */
/**
    * 字典类型表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictType implements Serializable {
    /**
    * 字典主键
    */
    private Long dictId;

    /**
    * 字典名称
    */
    private String dictName;

    /**
    * 字典类型
    */
    private String dictType;

    /**
    * 状态（0正常 1停用）
    */
    private Integer status;

    /**
    * 创建者
    */
    private String createBy;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新者
    */
    private String updateBy;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 备注
    */
    private String remark;

    private static final long serialVersionUID = 1L;
}