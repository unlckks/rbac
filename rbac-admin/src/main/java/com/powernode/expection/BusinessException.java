package com.powernode.expection;

import com.powernode.enums.ResultEnums;

/**
 * 进行业务异常处理
 * @Author: MingYun
 * @Date: 2023-02-03 15:01
 */
public class BusinessException  extends RuntimeException {
    private Integer errCode = ResultEnums.ERROR.getCode();

    public BusinessException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.errCode = resultEnums.getCode();
    }

    public BusinessException(ResultEnums resultEnums, Throwable throwable) {
        super(resultEnums.getMsg(), throwable);
        this.errCode = resultEnums.getCode();
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.errCode = code;
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
