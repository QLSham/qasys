package com.ps.qa.domain;

import java.io.Serializable;

/**
 * @description: 问卷明细表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysQresultT implements Serializable {

    private long id;
    private long mid;
    private long projectId;
    private String result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }


    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
