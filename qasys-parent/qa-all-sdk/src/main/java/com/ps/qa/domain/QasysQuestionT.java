package com.ps.qa.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * @description: 问题表
 * @author: ql-xrh
 * @create: 2019-07-18
 */
public class QasysQuestionT implements Serializable {

    private long id;
    private long user_id;
    private String question;
    private long integral;
    private String time;
    private long answerId;
    /**
     * 回答表id
     */
    private QasysAnswerT qasysAnswerT;
    /**
     * 会员id
     */
    private QasysUserT qasysUserT;

    @Override
    public String toString() {
        return "问题属性类 {" +
                "id=" + id +
                ", user_id=" + user_id +
                ", question='" + question + '\'' +
                ", integral=" + integral +
                ", time='" + time + '\'' +
                ", answerId=" + answerId +
                ", qasysAnswerT=" + qasysAnswerT +
                ", qasysUserT=" + qasysUserT +
                '}';
    }

    public QasysUserT getQasysUserT() {
        return qasysUserT;
    }

    public void setQasysUserT(QasysUserT qasysUserT) {
        this.qasysUserT = qasysUserT;
    }

    public QasysAnswerT getQasysAnswerT() {
        return qasysAnswerT;
    }

    public void setQasysAnswerT(QasysAnswerT qasysAnswerT) {
        this.qasysAnswerT = qasysAnswerT;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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
