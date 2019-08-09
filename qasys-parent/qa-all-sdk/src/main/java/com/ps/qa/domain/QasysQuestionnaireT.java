package com.ps.qa.domain;


import java.io.Serializable;
/**
 * @description: 问卷表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysQuestionnaireT implements Serializable {

    private long id;
    private String projectName;
    private long integral;
    private String time;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

}
