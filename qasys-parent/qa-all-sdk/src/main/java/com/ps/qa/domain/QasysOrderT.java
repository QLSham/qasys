package com.ps.qa.domain;

import java.io.Serializable;

/**
 * @description: 秒杀系统用户兑换商品订单
 * @author:QL
 * @create：2019/08/03
 */
public class QasysOrderT implements Serializable {
    private int id;
    private int user_id;
    private int com_id;
    private int ordertime;

    @Override
    public String toString() {
        return "QasysOrderT{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", com_id=" + com_id +
                ", ordertime=" + ordertime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }

    public int getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(int ordertime) {
        this.ordertime = ordertime;
    }
}
