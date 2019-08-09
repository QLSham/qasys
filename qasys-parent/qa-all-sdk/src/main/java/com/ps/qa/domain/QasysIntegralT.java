package com.ps.qa.domain;

import java.io.Serializable;

/**
 * @description: 积分流水表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysIntegralT implements Serializable {

    private long id;
    private long user_id;
    private long integral;
    private String time;
    private String state;
    private QasysUserT qasysUserT;

    @Override
    public String toString() {
        return "积分流水表属性类  {" +
                "id=" + id +
                ", user_id=" + user_id +
                ", integral=" + integral +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", qasysUserT=" + qasysUserT +
                '}';
    }

    public QasysUserT getQasysUserT() {
        return qasysUserT;
    }

    public void setQasysUserT(QasysUserT qasysUserT) {
        this.qasysUserT = qasysUserT;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
