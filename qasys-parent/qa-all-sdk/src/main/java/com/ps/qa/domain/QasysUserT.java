package com.ps.qa.domain;

import java.io.Serializable;
/**
 * @description: 用户表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysUserT implements Serializable {
    private long id;
    private String username;
    private String password;
    private long integral;
    private int code;

    @Override
    public String toString() {
        return "用户属性类 {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", integral=" + integral +
                ", code=" + code +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

}
