package com.ps.qa.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 问题表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysQuestionT2 implements Serializable {

    @Field("qId")
    private List<Integer> qId;
    @Field("question")
    private List<String> question;
    @Field("integral")
    private List<Integer> integral;
    @Field("qTime")
    private List<String> qTime;

    public List<Integer> getqId() {
        return qId;
    }

    public void setqId(List<Integer> qId) {
        this.qId = qId;
    }

    public List<String> getQuestion() {
        return question;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public List<Integer> getIntegral() {
        return integral;
    }

    public void setIntegral(List<Integer> integral) {
        this.integral = integral;
    }

    public List<String> getqTime() {
        return qTime;
    }

    public void setqTime(List<String> qTime) {
        this.qTime = qTime;
    }

    @Override
    public String toString() {
        return "---------{" +
                "qId=" + qId +
                ", question=" + question +
                ", integral=" + integral +
                ", qTime=" + qTime +
                '}';
    }
}
