package com.ps.qa.exception;


import java.io.Serializable;

/**
 * @description:统一异常类
 * @author:QL
 * @create：2019/07/04
 */
public class BusinessException extends RuntimeException implements Serializable {

    private Integer code;

    private String message;

    public BusinessException() {
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
