package com.ps.qa.domain;

import java.io.Serializable;

/**
 * @description:
 * @author:QL
 * @create：2019/07/19
 */
public class ResultVO<T> implements Serializable {
    private Integer code;
    private T data;
    private String message;

    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
