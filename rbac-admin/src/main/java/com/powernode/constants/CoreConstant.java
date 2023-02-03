package com.powernode.constants;

/**
 * 用于核心常量
 * @Author: MingYun
 * @Date: 2023-02-03 15:04
 */
public class CoreConstant {
    /**
     * 用户登陆地址
     */
    public static final String LOGIN_URL="/login";

    /**
     * 默认的父级菜单编号
     */
    public static final Long DEFAULT_PARENT_ID = 0L;

    /**
     * 菜单不显示
     */
    public static final Integer HIDDEN_STATE = 1;

    /**
     * 不跳转
     */
    public static final String NO_REDIRECT = "noRedirect";

    /**
     * 路径间隔符
     */
    public static final String URL_SPLIT = "/";

    /**
     * 菜单默认的组件地址
     */
    public static final String DEFAULT_COMPONENT = "Layout";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    //得到验证码的地址
    public static final String CAPTCHA_URL = "/captcha/getCaptcha";

    /**
     * 用于放session的user的key
     */
    public static final String SESSION_KEY="user";

    /**
     * 放权限的key
     */
    public static final String PERMISSIONS_KEY="permissions";

    /**
     * session中正确的验证码的key
     */
    public static final String CAPTCHA_KEY = "captcha";

    /**
     *  用户类型
     */
    public static final Integer USER_TYPE_ADMIN=0; //管理员
    public static final Integer USER_TYPE_NORMAL=1;//用户
}
