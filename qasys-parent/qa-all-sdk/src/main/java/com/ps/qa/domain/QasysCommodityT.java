package com.ps.qa.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 秒杀功能兑换商品属性类
 * @author:QL
 * @create：2019/08/03
 */
public class QasysCommodityT implements Serializable {
    private long id;
    private String name;
    private long exchangeIntegral;
    private int value;
    private String describes;
    private String startTime;
    private String endTime;
    private int version;

    @Override
    public String toString() {
        return "QasysCommodityT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exchangeIntegral=" + exchangeIntegral +
                ", value='" + value + '\'' +
                ", describe='" + describes + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExchangeIntegral() {
        return exchangeIntegral;
    }

    public void setExchangeIntegral(long exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
