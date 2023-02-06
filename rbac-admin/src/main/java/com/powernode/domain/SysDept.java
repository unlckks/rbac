package com.powernode.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @Author: MingYun
 *  @Date: 2023-02-06 16:38
 */
/**
    * 部门表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDept implements Serializable {
    /**
    * 部门id
    */
    private Long deptId;

    /**
    * 父部门id
    */
    private Long parentId;

    /**
    * 部门名称
    */
    private String deptName;

    /**
    * 显示顺序
    */
    private Integer orderNum;

    /**
    * 负责人
    */
    private String leader;

    /**
    * 联系电话
    */
    private String phone;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 部门状态（0正常 1停用）
    */
    private Integer status;

    /**
    * 删除标志（0存在 2删除）
    */
    private Integer delFlag;

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

    private static final long serialVersionUID = 1L;
}