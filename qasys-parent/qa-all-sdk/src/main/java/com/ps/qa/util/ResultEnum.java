package com.ps.qa.util;

/**
 * @description: ResultEnum
 * @author:QL
 * @create：2019/07/19
 */

public enum ResultEnum {
    SUCCESS(200, "成功"),
    ERROR(444, "操作失败"),
    PARAMETER_ERROR(403, "参数格式有误"),
    PASSWORD_ERROR(412, "密码有误"),
    NAME_EXIST(415, "账号已存在"),
    AUTH_CODE_inexistence(425, "不存在此验证码"),
    ANSER_BUG(455,"回答不全面"),
    StatusCode(459,"积分有误"),
    USER_NULL(500,"用户不存在"),
    INVENTORY_NULL(488,"商品库存不足"),
    EXCANGE_AGAIN(888,"您已经兑换过了此商品"),
    VERSION_NULL(555,"商品版本不一致"),
    blacklist_people(898,"您是黑名单用户,原因是发送请求太多次"),
    MORE_PEOPLER(898,"当前人数过多"),
    current_limiting(888,"限流中,请稍后再试");
    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

